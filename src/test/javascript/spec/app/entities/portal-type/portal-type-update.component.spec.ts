/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RiscScraperAdmTestModule } from '../../../test.module';
import { PortalTypeUpdateComponent } from 'app/entities/portal-type/portal-type-update.component';
import { PortalTypeService } from 'app/entities/portal-type/portal-type.service';
import { PortalType } from 'app/shared/model/portal-type.model';

describe('Component Tests', () => {
    describe('PortalType Management Update Component', () => {
        let comp: PortalTypeUpdateComponent;
        let fixture: ComponentFixture<PortalTypeUpdateComponent>;
        let service: PortalTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RiscScraperAdmTestModule],
                declarations: [PortalTypeUpdateComponent]
            })
                .overrideTemplate(PortalTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PortalTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PortalTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PortalType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.portalType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PortalType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.portalType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
