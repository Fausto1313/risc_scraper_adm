/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PortalService } from 'app/entities/portal/portal.service';
import { IPortal, Portal } from 'app/shared/model/portal.model';

describe('Service Tests', () => {
    describe('Portal Service', () => {
        let injector: TestBed;
        let service: PortalService;
        let httpMock: HttpTestingController;
        let elemDefault: IPortal;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PortalService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Portal(
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fecha: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Portal', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fecha: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fecha: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Portal(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Portal', async () => {
                const returnedFromService = Object.assign(
                    {
                        idscrapyData: 1,
                        name: 'BBBBBB',
                        domain: 'BBBBBB',
                        url: 'BBBBBB',
                        headerPath: 'BBBBBB',
                        bodyPath: 'BBBBBB',
                        autorPath: 'BBBBBB',
                        datePath: 'BBBBBB',
                        resumePath: 'BBBBBB',
                        path: 'BBBBBB',
                        xpath: true,
                        identifier: 'BBBBBB',
                        usuario: 'BBBBBB',
                        fecha: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fecha: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Portal', async () => {
                const returnedFromService = Object.assign(
                    {
                        idscrapyData: 1,
                        name: 'BBBBBB',
                        domain: 'BBBBBB',
                        url: 'BBBBBB',
                        headerPath: 'BBBBBB',
                        bodyPath: 'BBBBBB',
                        autorPath: 'BBBBBB',
                        datePath: 'BBBBBB',
                        resumePath: 'BBBBBB',
                        path: 'BBBBBB',
                        xpath: true,
                        identifier: 'BBBBBB',
                        usuario: 'BBBBBB',
                        fecha: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fecha: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Portal', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
