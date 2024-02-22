import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'app-confirm-email-page',
  templateUrl: './confirm-email-page.component.html',
  styleUrls: ['./confirm-email-page.component.scss'],
})
export class ConfirmEmailPageComponent implements OnInit {
  token: string;
  positiveEmailConfirm: boolean = false;
  negativeEmailConfirm: boolean = false;

  constructor(
    public faIconsService: FaIconsService,
    private authService: AuthService,
    private router: Router,
    protected commonFormServices: CommonFormServices,
    protected activatedRoute?: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.token = this.activatedRoute.snapshot.params.token as string;
    if (this.token) {
      this.authService.confirmEmail(this.token).subscribe({
        next: () => (this.positiveEmailConfirm = true),
        error: () => (this.negativeEmailConfirm = true),
      });
    }
  }

  navigate(): void {
    void this.router.navigate(['/']);
  }
}
