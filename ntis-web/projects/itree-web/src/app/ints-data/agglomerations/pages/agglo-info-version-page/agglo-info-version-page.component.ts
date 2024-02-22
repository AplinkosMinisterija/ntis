import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ActivatedRoute } from '@angular/router';
import { AgglomerationsService } from '../../services/agglomerations.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { NtisRejectedAggloVersion } from '@itree-commons/src/lib/model/api/api';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-agglo-info-version-page',
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
  templateUrl: './agglo-info-version-page.component.html',
  styleUrls: ['./agglo-info-version-page.component.scss'],
})
export class AggloInfoVersionPageComponent implements OnInit {
  readonly translationsRef = 'intsData.agglomerations.pages.aggloVersionInfo';
  destroy$: Subject<boolean> = new Subject<boolean>();

  data: NtisRejectedAggloVersion;
  notesString = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private agglomerationsService: AgglomerationsService,
    public fileUploadService: FileUploadService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      if (params['avId']) {
        this.agglomerationsService.getRejectionDetails(params['avId'] as string).subscribe((result) => {
          this.data = result;
          this.notesString = this.data.notes.join('\n');
        });
      }
    });
  }
}
