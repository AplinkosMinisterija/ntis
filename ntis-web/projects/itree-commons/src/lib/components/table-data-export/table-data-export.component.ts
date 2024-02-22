import { Component, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { TableExportService } from '@itree-commons/src/lib/services/table-export.service';
import { fadeInFields } from '../../animations/animations';
import { FaIconsService } from '../../services/fa-icons.service';
import { ExportFormatTypes, ExportThemes } from '../../enums/export-format-enums';
import { RowInput, UserOptions } from 'jspdf-autotable';
import { TableExportColumn } from '../../types/table-data-export';
import { TableColumn } from '../../model/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'spr-table-data-export',
  animations: [fadeInFields],
  templateUrl: './table-data-export.component.html',
  styleUrls: ['./table-data-export.component.scss'],
})
export class TableDataExportComponent implements OnInit, OnChanges {
  readonly exportFormatTypes = ExportFormatTypes;
  readonly exportThemes = ExportThemes;
  readonly translationsReference = 'common.date.dateAgo';

  showExportContent: boolean = false;
  exportAllData: boolean = true;
  exportAllCols: boolean = false;
  showExportOption: boolean = false;
  exportTheme: UserOptions['theme'] = this.exportThemes.grid;
  columnsForExport: TableExportColumn[] = [];
  valuesForXlsExport: RowInput[] = [];
  columnsList: TableExportColumn[] = [];
  wasInside: boolean = false;
  fileType: string = null;
  loadedAll: boolean = false;
  maxRowsDialog: boolean = false;
  isLoading: boolean = false;

  @Input() tableColumn: TableColumn[] = [];
  @Input() tableData: unknown[] = [];
  @Input() translation: string;
  @Input() fileName: string = 'file';
  @Input() translateFileName = true;
  @Input() showPdfExport = true;
  @Output() exportAllRecEvent = new EventEmitter<boolean>();

  @HostListener('click')
  clickInside(): void {
    this.wasInside = true;
  }

  @HostListener('document:click')
  clickOutside(): void {
    if (!this.maxRowsDialog) {
      if (!this.wasInside) {
        this.showExportContent = false;
        this.columnsForExport = [];
        this.valuesForXlsExport = [];
        this.exportAllCols = false;
      }
      this.wasInside = false;
    }
  }

  constructor(
    protected commonFormServices: CommonFormServices,
    private tableExportService: TableExportService,
    public faIconsService: FaIconsService
  ) {}

  ngOnInit(): void {
    this.showColumns();
    this.commonFormServices.translate.onLangChange.subscribe(() => {
      this.showColumns();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.tableData && this.fileType) {
      this.exportFile();
    } else {
      this.loadedAll = false;
    }
  }

  getDataFromQuery(): void {
    this.loadedAll = this.exportAllData;
    this.exportAllRecEvent.emit(this.exportAllData);
  }

  get getTableRows(): RowInput[] {
    return this.tableData as RowInput[];
  }
  set getTableRows(tableRowData: RowInput[]) {
    this.tableData = tableRowData;
  }

  selectAllCols(): void {
    this.exportAllCols = !this.exportAllCols;
    if (this.exportAllCols) {
      for (const item of this.columnsList) {
        this.addToExport(item);
      }
    } else {
      this.columnsForExport = [];
    }
  }

  addToExport(row: TableExportColumn): void {
    if (this.columnsForExport.includes(row)) {
      this.columnsForExport.splice(this.columnsForExport.indexOf(row), 1);
    } else {
      this.columnsForExport.push(row);
    }
    this.setValuesForXlsExport(this.columnsForExport);
  }

  setValuesForXlsExport(columns: TableExportColumn[]): void {
    this.valuesForXlsExport = [];
    for (const item of this.tableData) {
      const valueForExport = {} as RowInput;
      for (const col of columns) {
        Object.assign(valueForExport, { [col.dataKey]: item[col.dataKey as keyof typeof item] });
      }
      this.valuesForXlsExport.push(valueForExport);
    }
  }

  showColumns(): void {
    this.tableColumn = this.tableColumn.filter((item) => item.export === true);
    this.columnsList = [];
    this.tableColumn.forEach((col) => {
      this.commonFormServices.translate.get(this.translation + '.' + col.field).subscribe((text: string) => {
        this.columnsList.push({ title: text, dataKey: col.field });
      });
    });
  }

  getTableColumns(): TableExportColumn[] {
    if (this.columnsForExport.length > 0) {
      return this.columnsForExport;
    } else {
      return this.columnsList;
    }
  }

  getTableValuesXls(): RowInput[] {
    this.setValuesForXlsExport(this.columnsList);
    return this.valuesForXlsExport;
  }

  setExportTheme(value: UserOptions['theme']): void {
    this.exportTheme = value;
  }

  export(fileType: string): void {
    this.fileType = fileType;
    if (this.exportAllData === this.loadedAll) {
      this.exportFile();
    } else {
      this.fileType = fileType;
      this.getDataFromQuery();
    }
  }

  exportFile(): void {
    if (this.tableData.length >= MAX_ROWS_FOR_EXPORT) {
      this.maxRowsDialog = true;
    } else {
      this.prepareFile();
    }
  }

  prepareFile(): void {
    this.tableExportService.exportTable(
      this.fileType,
      this.getTableColumns(),
      this.getTableRows,
      this.getTableValuesXls(),
      this.exportTheme,
      this.translateFileName ? (this.commonFormServices.translate.instant(this.fileName) as string) : this.fileName
    );
    this.fileType = null;
    this.isLoading = false;
    this.maxRowsDialog = false;
  }

  closeDialog(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.prepareFile();
    }, 0);
  }
}
