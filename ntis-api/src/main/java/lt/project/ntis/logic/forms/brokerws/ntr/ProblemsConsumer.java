package lt.project.ntis.logic.forms.brokerws.ntr;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.text.TextStringBuilder;

import lt.project.ntis.logic.forms.brokerws.ntr.RcBrokerNtr.Problem;

/**
 * Pagalbinė integracijos su Registrų Centro AdrWS sistema klasė, naudojama kaupti atnaujinimo metu identifikuotas problemas.
 */
class ProblemsConsumer implements Consumer<Problem> {

    private final List<Problem> problems = new ArrayList<>();

    @Override
    public void accept(Problem problem) {
        if (problem != null) {
            problems.add(problem);
        }
    }

    boolean hasProblems() {
        return !problems.isEmpty();
    }

    @Override
    public String toString() {
        String result = "No problems found.";
        int cnt = problems.size();
        if (cnt > 0) {
            TextStringBuilder buf = new TextStringBuilder().append("Found problem(s): ").append(cnt).append(";\n");
            for (int i = 0; i < cnt; i++) {
                toBuffer(problems.get(i), i, cnt, buf);
            }
            result = buf.toString();
        }
        return result;
    }

    private void toBuffer(Problem problem, int index, int total, TextStringBuilder buf) {
        buf.append("  problem ").append(index + 1).append("/").append(total).append(" - transactionId: ").append(problem.transactionId()).append("; messsage: ")
                .append(problem.message()).append(".");
        Exception exception = problem.exception();
        if (exception != null) {
            buf.append("\n\t With exception: ");
            exception.printStackTrace(new PrintWriter(buf.asWriter(), true));
        }
        buf.append("\n");
    }

}
