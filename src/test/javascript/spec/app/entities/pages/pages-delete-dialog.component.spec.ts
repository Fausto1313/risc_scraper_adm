/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RiscScraperAdmTestModule } from '../../../test.module';
import { PagesDeleteDialogComponent } from 'app/entities/pages/pages-delete-dialog.component';
import { PagesService } from 'app/entities/pages/pages.service';

describe('Component Tests', () => {
    describe('Pages Management Delete Component', () => {
        let comp: PagesDeleteDialogComponent;
        let fixture: ComponentFixture<PagesDeleteDialogComponent>;
        let service: PagesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RiscScraperAdmTestModule],
                declarations: [PagesDeleteDialogComponent]
            })
                .overrideTemplate(PagesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
