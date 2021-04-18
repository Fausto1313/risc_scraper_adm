import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RiscScraperAdmSharedModule } from 'app/shared';
import { MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';

import {
    PagesComponent,
    PagesDetailComponent,
    PagesUpdateComponent,
    PagesDeletePopupComponent,
    PagesDeleteDialogComponent,
    pagesRoute,
    pagesPopupRoute
        
} from './';

const ENTITY_STATES = [...pagesRoute, ...pagesPopupRoute];

@NgModule({
    imports: [RiscScraperAdmSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule, BrowserAnimationsModule, MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, FlexLayoutModule],
    exports: [MatTableModule, MatSortModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, FlexLayoutModule],
    declarations: [PagesComponent, PagesDetailComponent, PagesUpdateComponent, PagesDeleteDialogComponent, PagesDeletePopupComponent],
    entryComponents: [PagesComponent, PagesUpdateComponent, PagesDeleteDialogComponent, PagesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RiscScraperAdmPagesModule {}
