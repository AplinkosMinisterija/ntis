package eu.itreegroup.spark.modules.admin.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.Date;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.gen.SprNewsDAOGen;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

public class SprNewsDAO extends SprNewsDAOGen {

    public SprNewsDAO() {
        super();
    }

    public SprNewsDAO(Double nw_id, String nw_type, String nw_lang, String nw_title, String nw_summary, String nw_text, String nw_content_for_search,
            Date nw_publication_date, Date nw_date_from, Date nw_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp,
            Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02,
            Date d03, Date d04, Date d05) {
        super(nw_id, nw_type, nw_lang, nw_title, nw_summary, nw_text, nw_content_for_search, nw_publication_date, nw_date_from, nw_date_to, rec_version,
                rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

    class Html2Text extends HTMLEditorKit.ParserCallback {

        StringBuilder s;

        public Html2Text() {
        }

        public String parse(Reader in) throws IOException {
            s = new StringBuilder();
            ParserDelegator delegator = new ParserDelegator();
            delegator.parse(in, this, Boolean.FALSE);
            return s.toString();
        }

        @Override
        public void handleText(char[] text, int pos) {
            s.append(text);
            s.append(" ");
        }

        public String getText() {
            return s.toString();
        }

    }

    /**
     * Before start validate object will be checked if data will be changes. In case if data was changed then will be
     * build search sring, by removind html elements and national characters.
     */
    @Override
    public void validateObject(int operation, SprBaseDBServiceImpl<?> baseService) throws SparkBusinessException {
        if (this.nw_title_changed || this.nw_summary_changed || this.nw_text_changed) {
            Html2Text html2Text = new Html2Text();
            StringBuilder s = new StringBuilder();
            s.append(this.getNw_title());
            s.append(" ");
            s.append(this.getNw_summary());
            s.append(" ");
            s.append(this.getNw_text());
            String serchContent;
            try {
                serchContent = html2Text.parse(new StringReader(s.toString()));
                serchContent = serchContent.toLowerCase();
                serchContent = Normalizer.normalize(serchContent, Normalizer.Form.NFD);
            } catch (IOException e) {
                e.printStackTrace();
                serchContent = s.toString();
                serchContent = serchContent.toLowerCase();
            }
            this.setNw_content_for_search(Utils.replaceNationalCharacters(serchContent));
        }
        super.validateObject(operation, baseService);
    }
}