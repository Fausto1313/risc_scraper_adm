import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RiscScraperAdmSharedModule } from 'app/shared';
import { MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import {
    PortalComponent,
    PortalDetailComponent,
    PortalUpdateComponent,
    PortalDeletePopupComponent,
    PortalDeleteDialogComponent,
    portalRoute,
    portalPopupRoute
} from './';

const ENTITY_STATES = [...portalRoute, ...portalPopupRoute];

@NgModule({
    imports: [RiscScraperAdmSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule, BrowserAnimationsModule, MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, FlexLayoutModule],
    exports: [MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, FlexLayoutModule],
    declarations: [PortalComponent, PortalDetailComponent, PortalUpdateComponent, PortalDeleteDialogComponent, PortalDeletePopupComponent],
    entryComponents: [PortalComponent, PortalUpdateComponent, PortalDeleteDialogComponent, PortalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RiscScraperAdmPortalModule {}
