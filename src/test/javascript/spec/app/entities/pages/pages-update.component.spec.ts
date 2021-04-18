/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RiscScraperAdmTestModule } from '../../../test.module';
import { PagesUpdateComponent } from 'app/entities/pages/pages-update.component';
import { PagesService } from 'app/entities/pages/pages.service';
import { Pages } from 'app/shared/model/pages.model';

describe('Component Tests', () => {
    describe('Pages Management Update Component', () => {
        let comp: PagesUpdateComponent;
        let fixture: ComponentFixture<PagesUpdateComponent>;
        let service: PagesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RiscScraperAdmTestModule],
                declarations: [PagesUpdateComponent]
            })
                .overrideTemplate(PagesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PagesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pages(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pages = entity;
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
                    const entity = new Pages();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pages = entity;
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
