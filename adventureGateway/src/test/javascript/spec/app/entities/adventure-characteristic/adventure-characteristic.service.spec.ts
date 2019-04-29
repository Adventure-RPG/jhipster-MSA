/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AdventureCharacteristicService } from 'app/entities/adventure-characteristic/adventure-characteristic.service';
import { IAdventureCharacteristic, AdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

describe('Service Tests', () => {
    describe('AdventureCharacteristic Service', () => {
        let injector: TestBed;
        let service: AdventureCharacteristicService;
        let httpMock: HttpTestingController;
        let elemDefault: IAdventureCharacteristic;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AdventureCharacteristicService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new AdventureCharacteristic('ID', 0, 0, 0, 0, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a AdventureCharacteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new AdventureCharacteristic(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a AdventureCharacteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        strength: 1,
                        agility: 1,
                        vitality: 1,
                        luck: 1,
                        intelligence: 1,
                        wisdom: 1,
                        charisma: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of AdventureCharacteristic', async () => {
                const returnedFromService = Object.assign(
                    {
                        strength: 1,
                        agility: 1,
                        vitality: 1,
                        luck: 1,
                        intelligence: 1,
                        wisdom: 1,
                        charisma: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a AdventureCharacteristic', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
