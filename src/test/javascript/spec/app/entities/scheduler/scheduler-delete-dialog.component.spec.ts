/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RiscScraperAdmTestModule } from '../../../test.module';
import { SchedulerDeleteDialogComponent } from 'app/entities/scheduler/scheduler-delete-dialog.component';
import { SchedulerService } from 'app/entities/scheduler/scheduler.service';

describe('Component Tests', () => {
    describe('Scheduler Management Delete Component', () => {
        let comp: SchedulerDeleteDialogComponent;
        let fixture: ComponentFixture<SchedulerDeleteDialogComponent>;
        let service: SchedulerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RiscScraperAdmTestModule],
                declarations: [SchedulerDeleteDialogComponent]
            })
                .overrideTemplate(SchedulerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SchedulerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchedulerService);
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
