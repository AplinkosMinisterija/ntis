export class SprPaging {
  cnt: number;
  skipRows: number;
  pageSize: number;
  orderClause: string;

  constructor(cnt: number, skipRows: number, pageSize: number, orderClause: string) {
    this.cnt = cnt;
    this.skipRows = skipRows;
    this.pageSize = pageSize;
    this.orderClause = orderClause;
  }
}
