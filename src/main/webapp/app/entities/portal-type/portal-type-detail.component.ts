import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPortalType } from 'app/shared/model/portal-type.model';

@Component({
    selector: 'jhi-portal-type-detail',
    templateUrl: './portal-type-detail.component.html'
})
export class PortalTypeDetailComponent implements OnInit {
    portalType: IPortalType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ portalType }) => {
            this.portalType = portalType;
        });
    }

    previousState() {
        window.history.back();
    }
}
