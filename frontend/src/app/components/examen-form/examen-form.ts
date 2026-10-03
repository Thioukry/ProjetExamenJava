import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExamenService } from '../../services/examen';

@Component({
  selector: 'app-examen-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './examen-form.html'
})
export class ExamenFormComponent {
  examenForm: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private examenService: ExamenService,
    private router: Router
  ) {
    this.examenForm = this.fb.group({
      titre: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required]],
      dureeMinutes: [60, [Validators.required, Validators.min(5)]],
      statut: ['OUVERT', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.examenForm.invalid) return;

    this.loading = true;
    this.examenService.createExamen(this.examenForm.value).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création de l\'examen.';
        this.loading = false;
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/dashboard']);
  }
}
