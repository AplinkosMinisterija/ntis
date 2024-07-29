package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import java.util.Arrays;
import lt.jmsys.spark.bind.annotations.Generated;
import lt.jmsys.spark.bind.annotations.Length;

@Generated
public class Spr_paging_ot implements Serializable {
	private final static long serialVersionUID = 1;

	private Double cnt;

	private Double skip_rows;

	private Double page_size;

	@Length(4000)
	private String order_clause;

	private Key_values_ot[] sum_values;

	public Spr_paging_ot() {
	}

	public Spr_paging_ot(Double cnt, Double skip_rows, Double page_size, String order_clause,
			Key_values_ot[] sum_values) {
		this.cnt = cnt;
		this.skip_rows = skip_rows;
		this.page_size = page_size;
		this.order_clause = order_clause;
		this.sum_values = sum_values;
	}

	public Spr_paging_ot(Spr_paging_ot src) {
		if (src == null) {
			return;
		}
		this.cnt = src.getCnt();
		this.skip_rows = src.getSkip_rows();
		this.page_size = src.getPage_size();
		this.order_clause = src.getOrder_clause();
		if (src.getSum_values() != null) {
			this.sum_values = new Key_values_ot[src.getSum_values().length];
			for (int i = 0; i < this.sum_values.length; i++) {
				this.sum_values[i] = new Key_values_ot(src.getSum_values()[i]);
			}
		}
	}

	public Double getCnt() {
		return this.cnt;
	}

	public void setCnt(Double cnt) {
		this.cnt = cnt;
	}

	public Double getSkip_rows() {
		return this.skip_rows;
	}

	public void setSkip_rows(Double skip_rows) {
		this.skip_rows = skip_rows;
	}

	public Double getPage_size() {
		return this.page_size;
	}

	public void setPage_size(Double page_size) {
		this.page_size = page_size;
	}

	public String getOrder_clause() {
		return this.order_clause;
	}

	public void setOrder_clause(String order_clause) {
		this.order_clause = order_clause;
	}

	public Key_values_ot[] getSum_values() {
		return this.sum_values;
	}

	public void setSum_values(Key_values_ot[] sum_values) {
		this.sum_values = sum_values;
	}

	@Override
	public String toString() {
		return Spr_paging_ot.class.getName() + "@[" + "cnt = " + cnt + ", skip_rows = " + skip_rows + ", page_size = "
				+ page_size + ", order_clause = " + order_clause + ", sum_values = " + Arrays.toString(sum_values)
				+ "]";
	}

}
