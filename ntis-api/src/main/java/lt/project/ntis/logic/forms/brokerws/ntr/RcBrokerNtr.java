package lt.project.ntis.logic.forms.brokerws.ntr;

import static lt.project.ntis.logic.forms.brokerws.ntr.RcBrokerNtrDb.getTransactionDateOfInterest;
import static lt.project.ntis.logic.forms.brokerws.ntr.RcBrokerNtrDb.toLocalDate;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import lt.project.ntis.brokerws.ntr761ws.GetReportResponse;
import lt.project.ntis.brokerws.ntr762ws.GetResultResponse;
import lt.project.ntis.logic.forms.brokerws.RcBrokerWs;

/**
 * Pagrindinė integracijos su Registrų Centro BrokerWS sistema klasė skirta masiniam NTR pasikeitimų atsisiuntimui ir pritaikymui.
 */
@Component
public class RcBrokerNtr {

    record Problem(String transactionId, String message, Exception exception) {

    }

    private static final Logger log = LoggerFactory.getLogger(RcBrokerNtr.class);

    static final String MDC_PROCESS_REQUEST_ID = "ntis:processRequestId";

    static final String MDC_TR_ID = "ntis:transactionId";

    static final String MDC_TR_DATE = "ntis:transactionDateOfInterest";

    static final String MDC_RESPONSE_STATUS = "ntis:responseStatus";

    static final String MDC_RESPONSE_MESSAGE = "ntis:responseMessage";

    static final String MDC_RESPONSE_REC_COUNT = "ntis:responseRecordsCount";

    @Autowired
    RcBrokerWs rcBrokerWs;

    @Autowired
    RcBrokerNtrJaxb rcBrokerNtrJaxb;

    @Autowired
    RcBrokerNtrDb rcBrokerNtrDb;

    void doJob(Connection conn, SprJobRequestsDAO jobRequest, LocalDate startDateInclusive, LocalDate endDateInclusive, Consumer<Problem> problemsConsumer)
            throws Exception {

        boolean updatesFound = collectResponses(conn, problemsConsumer);
        updatesFound |= sendRequests(conn, jobRequest, startDateInclusive, endDateInclusive, problemsConsumer);
        if (updatesFound) {
            rcBrokerNtrDb.createFacilitiesNtrs(conn);
        }
    }

    private boolean collectResponses(Connection conn, Consumer<Problem> problemsConsumer) throws Exception {

        boolean updatesFound = false;
        for (SprProcessRequestsDAO dao : rcBrokerNtrDb.loadProcessRequests(conn)) {
            updatesFound |= completePendingTransaction(conn, dao, problemsConsumer);
        }
        return updatesFound;
    }

    private boolean sendRequests(Connection conn, SprJobRequestsDAO jobRequest, LocalDate startDateInclusive, LocalDate endDateInclusive,
            Consumer<Problem> problemsConsumer) throws Exception {

        boolean updatesFound = false;
        if (startDateInclusive.isAfter(endDateInclusive)) {
            log.info("No new requests needed - already up to {} date. Last sunday was {}.", startDateInclusive, endDateInclusive);
        } else {
            for (LocalDate date : collectJobDates(startDateInclusive, endDateInclusive)) {
                updatesFound |= startTransaction(conn, jobRequest.getJrq_id(), date, problemsConsumer);
            }
            rcBrokerNtrDb.moveNextStartDate(conn, jobRequest, endDateInclusive);
        }
        return updatesFound;
    }

    private List<LocalDate> collectJobDates(LocalDate startDateInclusive, LocalDate endDateInclusive) {

        List<LocalDate> result = Collections.emptyList();
        LocalDate endDateExclusive = endDateInclusive.plusDays(1);
        if (startDateInclusive.isBefore(endDateExclusive)) {
            long limit = ChronoUnit.DAYS.between(startDateInclusive, endDateExclusive);
            result = Stream.iterate(startDateInclusive, date -> date.plusDays(1)).limit(limit).toList();
        }
        if (result.isEmpty()) {
            log.info("No new NTR batch updates are needed at this time.");
        } else {
            log.info("Collected new dates for NTR batch updates: {}.", result);
        }
        return result;
    }

    private boolean completePendingTransaction(Connection conn, SprProcessRequestsDAO processRequest, Consumer<Problem> problemsConsumer) throws Exception {

        MDC.put(MDC_PROCESS_REQUEST_ID, Long.toString(processRequest.getPrq_id().longValue()));
        MDC.put(MDC_TR_ID, processRequest.getPrq_token());
        MDC.put(MDC_TR_DATE, toLocalDate(getTransactionDateOfInterest(processRequest)).toString());
        log.info("START processRequest {} (transactionId:{}; date:{}).", MDC.get(MDC_PROCESS_REQUEST_ID), MDC.get(MDC_TR_ID), MDC.get(MDC_TR_DATE));

        boolean updatesFound = false;
        try {
            String responseXml = rcBrokerWs.checkNtrTransaction762(processRequest.getPrq_token());
            GetResultResponse response = rcBrokerNtrJaxb.parseGetResultResponse(responseXml);

            MDC.put(MDC_RESPONSE_STATUS, response.getStatus().name());
            MDC.put(MDC_RESPONSE_MESSAGE, response.getMessage());
            MDC.put(MDC_RESPONSE_REC_COUNT, response.getRecords() == null ? "null" : Long.toString(response.getRecords()));

            switch (response.getStatus()) {
                case CREATED, PROCESSING:
                    handleIncompleteness(conn, processRequest, problemsConsumer);
                    break;
                case COMPLETED:
                    updatesFound = handleCompletion(conn, processRequest, response, problemsConsumer);
                    break;
                case ERROR:
                    handleError(conn, processRequest, problemsConsumer);
                    break;
            }
        } catch (Exception e) {
            String message = String.format("Failed to complete pending transaction '%s' for date %s.", MDC.get(MDC_TR_ID), MDC.get(MDC_TR_DATE));
            log.error(message, e);
            problemsConsumer.accept(new Problem(null, message, e));

            conn.rollback();

        } finally {
            log.info("FINISH processRequest {} (transactionId:{}).", MDC.get(MDC_PROCESS_REQUEST_ID), MDC.get(MDC_TR_ID));
            rcBrokerNtrDb.closeProcessRequest(conn, processRequest);
        }
        return updatesFound;
    }

    private boolean startTransaction(Connection conn, Double jobRequestId, LocalDate dateOfInterest, Consumer<Problem> problemsConsumer) throws Exception {

        boolean updatesFound = false;
        try {
            String responseXml = rcBrokerWs.startNtrTransaction761(dateOfInterest);
            GetReportResponse response = rcBrokerNtrJaxb.parseGetReportResponse(responseXml);

            MDC.put(MDC_TR_ID, response.getTransactionId());
            MDC.put(MDC_TR_DATE, dateOfInterest.toString());
            MDC.put(MDC_RESPONSE_STATUS, response.getStatus().name());
            MDC.put(MDC_RESPONSE_MESSAGE, response.getMessage());
            MDC.put(MDC_RESPONSE_REC_COUNT, response.getRecords() == null ? "null" : Long.toString(response.getRecords()));

            switch (response.getStatus()) {
                case CREATED, PROCESSING:
                    rcBrokerNtrDb.handleTransactionStart(conn, jobRequestId, dateOfInterest, response.getTransactionId());
                    break;
                case COMPLETED:
                    updatesFound = handleCompletion(conn, response, problemsConsumer);
                    break;
                case ERROR:
                    handleError(problemsConsumer);
                    break;
            }
        } catch (Exception e) {
            String message = String.format("Failed to start transaction for date %s.", MDC.get(MDC_TR_DATE));
            log.error(message, e);
            problemsConsumer.accept(new Problem(null, message, e));
        }
        return updatesFound;
    }

    private void handleIncompleteness(Connection conn, SprProcessRequestsDAO request, Consumer<Problem> problemsConsumer) throws Exception {

        String message = String.format("Transaction '%s' for date %s did not reach final state in a reasonable amount of time.", MDC.get(MDC_TR_ID),
                MDC.get(MDC_TR_DATE));

        problemsConsumer.accept(new Problem(MDC.get(MDC_TR_ID), message, null));
    }

    private boolean handleCompletion(Connection conn, GetReportResponse response, Consumer<Problem> problemsConsumer) throws Exception {

        return handleCompletion(conn, null, response.getRecords(), response.getData(), problemsConsumer);
    }

    private boolean handleCompletion(Connection conn, SprProcessRequestsDAO processRequest, GetResultResponse response, Consumer<Problem> problemsConsumer)
            throws Exception {

        return handleCompletion(conn, processRequest, response.getRecords(), response.getData(), problemsConsumer);
    }

    private boolean handleCompletion(Connection conn, SprProcessRequestsDAO processRequest, Long recordsCount, byte[] data, Consumer<Problem> problemsConsumer)
            throws Exception {

        log.info("Transaction '{}' for date {} completed with {} records.", MDC.get(MDC_TR_ID), MDC.get(MDC_TR_DATE), recordsCount);
        boolean updatesFound = false;
        if (recordsCount > 0) {
            try {
                rcBrokerNtrDb.applyDataset(conn, rcBrokerNtrJaxb.parseDataset(data), problemsConsumer);
                updatesFound = true;

            } catch (Exception e) {
                String exceptionMessage = String.format("Failed to apply received dataset for transaction %s (date %s).", MDC.get(MDC_TR_ID),
                        getTransactionDateOfInterest(processRequest));
                log.error(exceptionMessage, e);
                problemsConsumer.accept(new Problem(MDC.get(MDC_TR_ID), exceptionMessage, e));

                conn.rollback();
            }
        }
        return updatesFound;
    }

    private void handleError(Consumer<Problem> problemsConsumer) throws Exception {

        String message = String.format("Failed to start transaction for date %s - RC response status:%s; message:%s; records:%s.", MDC.get(MDC_TR_DATE),
                MDC.get(MDC_RESPONSE_STATUS), MDC.get(MDC_RESPONSE_MESSAGE), MDC.get(MDC_RESPONSE_REC_COUNT));

        log.error(message);
        problemsConsumer.accept(new Problem(null, message, null));
    }

    private void handleError(Connection conn, SprProcessRequestsDAO processRequest, Consumer<Problem> problemsConsumer) throws Exception {

        String message = String.format("Transaction '%s' for date %s completed with error - RC response status:%s; message:%s; records:%s.", MDC.get(MDC_TR_ID),
                MDC.get(MDC_TR_DATE), MDC.get(MDC_RESPONSE_STATUS), MDC.get(MDC_RESPONSE_MESSAGE), MDC.get(MDC_RESPONSE_REC_COUNT));

        log.error(message);
        problemsConsumer.accept(new Problem(MDC.get(MDC_TR_ID), message, null));
    }

}
