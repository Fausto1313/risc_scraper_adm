import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPortalType } from 'app/shared/model/portal-type.model';
import { PortalTypeService } from './portal-type.service';

@Component({
    selector: 'jhi-portal-type-delete-dialog',
    templateUrl: './portal-type-delete-dialog.component.html'
})
export class PortalTypeDeleteDialogComponent {
    portalType: IPortalType;

    constructor(private portalTypeService: PortalTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.portalTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'portalTypeListModification',
                content: 'Deleted an portalType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-portal-type-delete-popup',
    template: ''
})
export class PortalTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ portalType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PortalTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.portalType = portalType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
