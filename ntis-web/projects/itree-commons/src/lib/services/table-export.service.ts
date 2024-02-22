import { Injectable } from '@angular/core';
import { ExportToCsv, Options } from 'export-to-csv';
import FileSaver from 'file-saver';
import jsPDF from 'jspdf';
import autoTable, { CellInput, ColumnInput, RowInput, UserOptions } from 'jspdf-autotable';
import { FILE_TYPE_CSV, FILE_TYPE_PDF, FILE_TYPE_XLS } from '../../constants/classificators.constants';
import * as xlsx from 'xlsx';
import { exportFonts } from '../../../../itree-web/src/assets/fonts/font-Base64';
@Injectable({
  providedIn: 'root',
})
export class TableExportService {
  exportTable(
    fileType: string,
    columns: UserOptions['columns'],
    body: UserOptions['body'],
    bodyXls: RowInput[],
    theme: UserOptions['theme'],
    filename = 'data'
  ): void {
    switch (fileType) {
      case FILE_TYPE_CSV:
        this.exportCSV(columns, body, filename);
        break;
      case FILE_TYPE_PDF:
        this.exportPdf(columns, body, theme, filename);
        break;
      case FILE_TYPE_XLS:
        this.exportExcel(columns, bodyXls, filename);
        break;
    }
  }

  exportCSV(columns: ColumnInput[], body: UserOptions['body'], filename?: string, exportOptions: Options = {}): void {
    const options: Options = {
      fieldSeparator: ',',
      quoteStrings: '"',
      decimalSeparator: '.',
      showLabels: false,
      showTitle: false,
      filename: filename,
      useTextFile: false,
      useBom: true,
      useKeysAsHeaders: true,
      ...(exportOptions || {}),
    };
    const objArr = [];
    const header: Record<string | number, CellInput> = {};
    columns.forEach((col) => {
      if (typeof col === 'object') {
        header[col.dataKey] = col.title;
      }
    });
    objArr.push(header);
    if (body) {
      body.forEach((bodyItem) => {
        const obj: Record<string | number, CellInput> = {};
        columns.forEach((col) => {
          if (typeof col === 'object' && typeof bodyItem === 'object') {
            bodyItem = bodyItem as Record<string | number, CellInput>;
            obj[col.dataKey] = bodyItem[col.dataKey];
          }
        });
        objArr.push(obj);
      });
    }

    const json = JSON.stringify(objArr, (_key, value) => (value === undefined ? '' : (value as unknown)));
    const csvExporter = new ExportToCsv(options);
    csvExporter.generateCsv(json);
  }

  exportPdf(columns: ColumnInput[], body: RowInput[], theme: UserOptions['theme'], filename?: string): void {
    const doc = new jsPDF('p', 'pt');
    doc.addFileToVFS('Roboto.ttf', exportFonts);
    doc.addFont('Roboto.ttf', 'Roboto', 'normal');
    autoTable(doc, {
      columns,
      body,
      theme,
      styles: {
        font: 'Roboto',
      },
    });
    doc.save(this.getFileName(filename ? filename : 'data', 'pdf'));
  }

  exportExcel(columns: ColumnInput[], data: RowInput[], filename?: string): void {
    const headings = [
      columns
        .filter((column) => typeof column === 'object')
        .map((column) => typeof column === 'object' && column.title),
    ];
    const ws = xlsx.utils.json_to_sheet(data);
    ws['!cols'] = [];
    ws['!cols']['7'] = { hidden: true };
    const workbook = { Sheets: { data: ws }, SheetNames: ['data'] };
    xlsx.utils.sheet_add_json(workbook, data, { skipHeader: true, origin: 'A2' });
    xlsx.utils.sheet_add_aoa(ws, headings);
    const excelBuffer = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' }) as BlobPart;
    this.saveAsExcelFile(excelBuffer, filename || 'data');
  }

  saveAsExcelFile(buffer: BlobPart, fileName: string): void {
    const type = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    const extension = '.xlsx';
    const data: Blob = new Blob([buffer], {
      type,
    });
    FileSaver.saveAs(data, this.getFileName(fileName, extension));
  }

  getFileName(fileName: string, extension: string): string {
    return fileName + '_export_' + new Date().toISOString().substring(0, 10) + extension;
  }

  exportXML(
    outerTags: { openingTag: string; closingTag: string },
    columns: ColumnInput[],
    filename: string,
    useBom: boolean = false
  ): void {
    let xmlContent = `<?xml version="1.0" encoding="UTF-8"?>\n`;

    xmlContent += `<${outerTags.openingTag}>\n`;
    xmlContent += `\t<${outerTags.closingTag}>\n`;

    columns.forEach((col) => {
      if (typeof col === 'object') {
        const tag = (col.title as string).replace(/ /g, '_');
        xmlContent += `\t\t<${tag}></${tag}>\n`;
      }
    });

    xmlContent += `\t</${outerTags.closingTag}>\n`;
    xmlContent += `</${outerTags.openingTag}>`;

    const blob = new Blob([useBom ? '\uFEFF' + xmlContent : xmlContent], { type: 'text/xml;charset=utf-8' });
    FileSaver.saveAs(blob, filename);
  }
}
