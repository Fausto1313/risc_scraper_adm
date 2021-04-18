/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PagesService } from 'app/entities/pages/pages.service';
import { IPages, Pages } from 'app/shared/model/pages.model';

describe('Service Tests', () => {
    describe('Pages Service', () => {
        let injector: TestBed;
        let service: PagesService;
        let httpMock: HttpTestingController;
        let elemDefault: IPages;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PagesService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Pages(
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
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        date: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Pages', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        date: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Pages(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Pages', async () => {
                const returnedFromService = Object.assign(
                    {
                        idscrapyData: 1,
                        headerData: 'BBBBBB',
                        bodyData: 'BBBBBB',
                        autorData: 'BBBBBB',
                        dateData: 'BBBBBB',
                        headerClean: 'BBBBBB',
                        bodyClean: 'BBBBBB',
                        autorClean: 'BBBBBB',
                        dateClean: 'BBBBBB',
                        resumeClean: 'BBBBBB',
                        resumeData: 'BBBBBB',
                        screenshot: 'BBBBBB',
                        date: currentDate.format(DATE_TIME_FORMAT),
                        fullPath: 'BBBBBB',
                        url: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        date: currentDate
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

            it('should return a list of Pages', async () => {
                const returnedFromService = Object.assign(
                    {
                        idscrapyData: 1,
                        headerData: 'BBBBBB',
                        bodyData: 'BBBBBB',
                        autorData: 'BBBBBB',
                        dateData: 'BBBBBB',
                        headerClean: 'BBBBBB',
                        bodyClean: 'BBBBBB',
                        autorClean: 'BBBBBB',
                        dateClean: 'BBBBBB',
                        resumeClean: 'BBBBBB',
                        resumeData: 'BBBBBB',
                        screenshot: 'BBBBBB',
                        date: currentDate.format(DATE_TIME_FORMAT),
                        fullPath: 'BBBBBB',
                        url: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        date: currentDate
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

            it('should delete a Pages', async () => {
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
