import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPages } from 'app/shared/model/pages.model';
import { PagesService } from './pages.service';
import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from 'app/entities/portal';

@Component({
    selector: 'jhi-pages-update',
    templateUrl: './pages-update.component.html'
})
export class PagesUpdateComponent implements OnInit {
    pages: IPages;
    isSaving: boolean;

    portals: IPortal[];
    date: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private pagesService: PagesService,
        private portalService: PortalService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pages }) => {
            this.pages = pages;
            this.date = this.pages.date != null ? this.pages.date.format(DATE_TIME_FORMAT) : null;
        });
        this.portalService.query().subscribe(
            (res: HttpResponse<IPortal[]>) => {
                this.portals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.pages, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pages.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.pages.id !== undefined) {
            this.subscribeToSaveResponse(this.pagesService.update(this.pages));
        } else {
            this.subscribeToSaveResponse(this.pagesService.create(this.pages));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPages>>) {
        result.subscribe((res: HttpResponse<IPages>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPortalById(index: number, item: IPortal) {
        return item.id;
    }
}
