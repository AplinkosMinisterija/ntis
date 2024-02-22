import { Component, Input } from '@angular/core';

@Component({
  selector: 'spr-editor-viewer',
  templateUrl: './editor-viewer.component.html',
  styleUrls: ['./editor-viewer.component.scss'],
})
export class EditorViewerComponent {
  @Input() content: string;
}
