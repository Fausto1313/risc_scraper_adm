/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RiscScraperAdmTestModule } from '../../../test.module';
import { PortalTypeDetailComponent } from 'app/entities/portal-type/portal-type-detail.component';
import { PortalType } from 'app/shared/model/portal-type.model';

describe('Component Tests', () => {
    describe('PortalType Management Detail Component', () => {
        let comp: PortalTypeDetailComponent;
        let fixture: ComponentFixture<PortalTypeDetailComponent>;
        const route = ({ data: of({ portalType: new PortalType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RiscScraperAdmTestModule],
                declarations: [PortalTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PortalTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PortalTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.portalType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
