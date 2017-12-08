import {NgModule} from '@angular/core';

import {
  MatButtonModule, MatMenuModule, MatToolbarModule, MatSidenavModule, MatListModule, MatTooltipModule,
  MatIconModule, MatCardModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule, MatSnackBarModule
} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule, MatMenuModule, MatToolbarModule, MatSidenavModule, MatListModule, MatIconModule, MatCardModule, MatFormFieldModule,
    MatInputModule, MatProgressSpinnerModule, MatSnackBarModule, MatTooltipModule
  ],
  exports: [
    MatButtonModule, MatMenuModule, MatToolbarModule, MatSidenavModule, MatListModule, MatIconModule, MatCardModule, MatFormFieldModule,
    MatInputModule, MatProgressSpinnerModule, MatSnackBarModule, MatTooltipModule
  ]
})
export class MaterialModule {}
