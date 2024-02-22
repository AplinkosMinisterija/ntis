import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NtisContractRequestComment } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-comments',
  standalone: true,
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
  imports: [CommonModule, ItreeCommonsModule, FontAwesomeModule, FormsModule],
})
export class CommentsComponent {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.components.comments';

  @Input() comments: NtisContractRequestComment[];
  @Input() canDeleteComment: boolean = false;
  @Output() addCommentEvent = new EventEmitter<string>();
  @Output() deleteCommentEvent = new EventEmitter<NtisContractRequestComment>();

  addCommentMode = false;
  commentText: string;

  constructor(public faIconsService: FaIconsService) {}

  handleAddComment(): void {
    this.addCommentEvent.emit(this.commentText);
    this.commentText = '';
    this.addCommentMode = false;
  }
}
