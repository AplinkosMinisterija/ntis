import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FileUploadWithProgressComponent } from './file-upload-with-progress.component';

describe('FileUploadWithProgressComponent', () => {
  let component: FileUploadWithProgressComponent;
  let fixture: ComponentFixture<FileUploadWithProgressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FileUploadWithProgressComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FileUploadWithProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
