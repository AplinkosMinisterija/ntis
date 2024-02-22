import { Component, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';
import { Papa } from 'ngx-papaparse';
import { TableColumn } from '../../model/browse-pages';
import { TableExportService } from '../../services/table-export.service';
import { TableExportColumn } from '../../types/table-data-export';
import { ExportFormatTypes } from '../../enums/export-format-enums';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { SprRefCodesDAO } from '../../model/api/api';
import { AdminService } from '@itree-web/src/app/spark-admin-module/admin-services/admin.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'spr-table-data-import',
  templateUrl: './table-data-import.component.html',
  styleUrls: ['./table-data-import.component.scss'],
})
export class TableDataImportComponent implements OnInit {
  readonly exportFormatTypes = ExportFormatTypes;
  readonly translationsReference = 'components.tableDataImport';
  readonly translationsReferenceXml = 'components.tableDataImportXml';

  wasInside: boolean = false;
  showImportContent: boolean = false;
  columnsList: TableExportColumn[] = [];
  importDialogVisible: boolean = false;
  loading: boolean = false;
  codesListImport: SprRefCodesDAO[] = [] as SprRefCodesDAO[];
  fileInput: File;
  rfdId: string = null;
  tableName: string = null;
  errorCounter: number = 0;

  @Input() fileName: string = 'file';
  @Input() tableColumn: TableColumn[] = [];
  @Input() translation: string;
  @Output() reloadData = new EventEmitter();

  @ViewChild('file') file: HTMLInputElement;

  @HostListener('click')
  clickInside(): void {
    this.wasInside = true;
  }

  @HostListener('document:click')
  clickOutside(): void {
    if (!this.wasInside) {
      this.showImportContent = false;
    }
    this.wasInside = false;
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showImportContent = false;
  }

  constructor(
    protected commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private tableExportService: TableExportService,
    private papa: Papa,
    private adminService: AdminService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.rfdId = this.activatedRoute.snapshot.params['id'] as string;
    this.tableName = this.activatedRoute.snapshot.params['tableName'] as string;
    this.showColumns();
  }

  onFileSelected(event: Event): void {
    this.loading = true;
    this.showImportContent = false;
    const file: File = (event.target as HTMLInputElement).files[0];

    if (file.type === 'text/csv') {
      this.papa.parse(file, {
        header: true,
        dynamicTyping: true,
        skipEmptyLines: 'greedy',
        transformHeader: (header) => {
          this.commonFormServices.translate.get(this.translation).subscribe((translate: Record<string, string>) => {
            header = Object.keys(translate).find((key) => {
              return translate[key] === header.replace(/["]+/g, '');
            });
          });
          return header;
        },
        complete: (results) => {
          this.errorCounter = 0;
          this.commonFormServices.appMessages.clearMessages();

          if (results.errors && results.errors?.length > 0) {
            results.errors.forEach((error) => {
              if (error.code === 'TooManyFields') {
                const fields = error.message.match(/\d+/g);
                this.commonFormServices.translate
                  .get(this.translationsReference + '.tooManyFieldsError', {
                    rowNum: error.row,
                    expNum: fields[0],
                    parsedNum: fields[1],
                  })
                  .subscribe((translation: string) => {
                    this.commonFormServices.appMessages.showError(error.code, translation);
                  });
                this.resetImportValues();
              } else if (error.code === 'TooFewFields') {
                this.commonFormServices.translate
                  .get(this.translationsReference + '.tooFewFieldsError', { row: error.row + 1 })
                  .subscribe((translation: string) => {
                    this.commonFormServices.appMessages.showError(error.code, translation);
                  });
                this.resetImportValues();
              }
            });
          } else if (results.data) {
            this.validateResults(results.data as SprRefCodesDAO[], file.type);
          }
        },
      });
    } else if (file.type === 'text/xml') {
      const fileReader = new FileReader();

      fileReader.onload = (): void => {
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(fileReader.result as string, 'text/xml');
        const items = xmlDoc.getElementsByTagName('Įrašas');

        const results = Array.from(items).map((item) => {
          const kodas = item.getElementsByTagName('Kodas')[0]?.textContent;
          const reiksme = item.getElementsByTagName('Reikšmė')[0]?.textContent;
          const aprasymas = item.getElementsByTagName('Aprašymas')[0]?.textContent;
          const galiojaNuo = item.getElementsByTagName('Galioja_nuo')[0]?.textContent;
          const galiojaIki = item.getElementsByTagName('Galioja_iki')[0]?.textContent;
          const kodoEiliskumas = item.getElementsByTagName('Kodo_eiliškumas')[0]?.textContent;

          return {
            rfc_code: kodas,
            rfc_meaning: reiksme,
            rfc_description: aprasymas,
            rfc_date_from: galiojaNuo ? new Date(galiojaNuo) : null,
            rfc_date_to: galiojaIki ? new Date(galiojaIki) : null,
            rfc_order: parseInt(kodoEiliskumas),
          } as Partial<SprRefCodesDAO>;
        });
        this.validateResults(results as SprRefCodesDAO[], file.type);
      };

      if (file) {
        fileReader.readAsText(file);
      }
    } else {
      this.commonFormServices.translate
        .get(this.translationsReference + '.wrongFileTypeError')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('WrongFileTypeError', translation);
        });
      this.resetImportValues();
    }
  }

  validateResults(results: SprRefCodesDAO[], fileType: string): void {
    this.errorCounter = 0;
    this.commonFormServices.appMessages.clearMessages();
    this.codesListImport = results;

    results.forEach((item, index) => {
      const currentRow = index + 1;
      item.rfc_date_from = this.checkDateFrom(item.rfc_date_from, currentRow, fileType);
      item.rfc_date_to = this.checkDateTo(item.rfc_date_to, currentRow, fileType);
      if (Object.keys(item).length != 6) {
        this.commonFormServices.translate
          .get(this.translationsReference + '.rowColumnMismatchError')
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showError('', translation);
            this.errorCounter++;
          });
        this.resetImportValues();
      } else if (!item.rfc_code || !item.rfc_meaning) {
        if (!item.rfc_code) {
          this.throwError('.missingClassifierCodeError', currentRow, fileType);
        }
        if (!item.rfc_meaning) {
          this.throwError('.missingClassifierMeaningError', currentRow, fileType);
        }
      }
    });

    if (this.errorCounter === 0) {
      this.importDialogVisible = true;
    } else {
      this.resetImportValues();
    }
    this.loading = false;
  }

  checkDateFrom(date: Date, currentRow: number, fileType: string): Date {
    if (date) {
      date = new Date(date);
      if (isNaN(date.getTime())) {
        this.throwError('.dateFormatError', currentRow, fileType);
        return null;
      } else {
        return date;
      }
    } else {
      this.throwError('.dateNullError', currentRow, fileType);
      return null;
    }
  }

  checkDateTo(date: Date, currentRow: number, fileType: string): Date {
    if (date) {
      date = new Date(date);
      if (isNaN(date.getTime())) {
        this.throwError('.dateFormatError', currentRow, fileType);
        return null;
      } else {
        return date;
      }
    } else {
      return null;
    }
  }

  throwError(message: string, currentRow: number, fileType: string): void {
    if (fileType === 'text/xml') {
      this.commonFormServices.translate
        .get(this.translationsReferenceXml + message)
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
          this.errorCounter++;
        });
    } else {
      this.commonFormServices.translate
        .get(this.translationsReference + message, {
          row: currentRow,
        })
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
          this.errorCounter++;
        });
    }
  }

  showColumns(): void {
    this.tableColumn = this.tableColumn.filter((item) => item.export === true);
    this.commonFormServices.translate.get(this.translation).subscribe((translate: Record<string, string>) => {
      this.columnsList = this.tableColumn.map((col) => ({
        title: translate[col.field],
        dataKey: col.field,
      }));
    });
  }

  export(fileType: string): void {
    this.wasInside = true;
    if (fileType === this.exportFormatTypes.xml) {
      const outerTags = {
        openingTag: 'Sąrašas',
        closingTag: 'Įrašas',
      };
      this.tableExportService.exportXML(outerTags, this.columnsList, this.fileName);
    } else {
      this.tableExportService.exportTable(fileType, this.columnsList, null, null, null, this.fileName);
    }
  }

  onCodesImport(): void {
    this.codesListImport.forEach((item) => {
      item.rfc_rfd_id = Number(this.rfdId);
      item.rfc_domain = this.tableName;
    });
    this.adminService.setRefCodeRecords(this.codesListImport).subscribe(() => {
      this.resetImportValues();
      this.reloadData.emit();
    });
  }

  resetImportValues(): void {
    this.importDialogVisible = false;
    this.codesListImport = [];
    this.fileInput = null;
    this.loading = false;
  }
}
