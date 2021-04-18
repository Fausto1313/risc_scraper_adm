import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPortalType } from 'app/shared/model/portal-type.model';
import { PortalTypeService } from './portal-type.service';

@Component({
    selector: 'jhi-portal-type-update',
    templateUrl: './portal-type-update.component.html'
})
export class PortalTypeUpdateComponent implements OnInit {
    portalType: IPortalType;
    isSaving: boolean;

    constructor(private portalTypeService: PortalTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ portalType }) => {
            this.portalType = portalType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.portalType.id !== undefined) {
            this.subscribeToSaveResponse(this.portalTypeService.update(this.portalType));
        } else {
            this.subscribeToSaveResponse(this.portalTypeService.create(this.portalType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPortalType>>) {
        result.subscribe((res: HttpResponse<IPortalType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
