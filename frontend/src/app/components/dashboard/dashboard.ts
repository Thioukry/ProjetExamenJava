import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService, UserTokenPayload } from '../../services/auth.service';
import { ExamenService, Examen } from '../../services/examen';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html'
})
export class DashboardComponent implements OnInit {
  user: UserTokenPayload | null = null;
  examens: Examen[] = [];
  loading = true;
  isAdmin = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private examenService: ExamenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUserInfo();
    this.isAdmin = this.authService.hasRole('ADMIN') || this.authService.hasRole('ROLE_ADMIN');
    this.chargerExamens();
  }

  chargerExamens(): void {
    this.loading = true;
    this.errorMessage = '';

    this.examenService.getExamens().subscribe({
      next: (data) => {
        this.examens = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des examens', err);
        if (err.status === 401 || err.status === 403) {
          this.errorMessage = 'Accès refusé ou session expirée. Veuillez vous re-connecter.';
        } else {
          this.errorMessage = 'Impossible de charger la liste des examens. Vérifiez le backend.';
        }
        this.loading = false;
      }
    });
  }

  onDelete(id?: number): void {
    if (!id) return;
    if (confirm('Voulez-vous vraiment supprimer cet examen ?')) {
      this.examenService.deleteExamen(id).subscribe({
        next: () => this.chargerExamens(),
        error: (err) => {
          console.error('Erreur lors de la suppression', err);
          alert('Impossible de supprimer cet examen.');
        }
      });
    }
  }

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
