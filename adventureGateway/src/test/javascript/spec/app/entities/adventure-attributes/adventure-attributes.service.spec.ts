/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AdventureAttributesService } from 'app/entities/adventure-attributes/adventure-attributes.service';
import {
  IAdventureAttributes,
  AdventureAttributes,
  AdventureDefenceArmorType,
  AdventureActiveWeaponAttackDamage
} from 'app/shared/model/adventure-attributes.model';

describe('Service Tests', () => {
  describe('AdventureAttributes Service', () => {
    let injector: TestBed;
    let service: AdventureAttributesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdventureAttributes;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AdventureAttributesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AdventureAttributes(
        'ID',
        0,
        AdventureDefenceArmorType.BASIC,
        0,
        0,
        0,
        0,
        AdventureActiveWeaponAttackDamage.BLUNT,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AdventureAttributes', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new AdventureAttributes(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AdventureAttributes', async () => {
        const returnedFromService = Object.assign(
          {
            defence: 1,
            defenceArmorType: 'BBBBBB',
            fireResistance: 1,
            earthResistance: 1,
            waterResistance: 1,
            windResistance: 1,
            activeWeaponAttackDamage: 'BBBBBB',
            activeWeaponAttackHit: 1,
            activeWeaponAttackType: 1,
            size: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AdventureAttributes', async () => {
        const returnedFromService = Object.assign(
          {
            defence: 1,
            defenceArmorType: 'BBBBBB',
            fireResistance: 1,
            earthResistance: 1,
            waterResistance: 1,
            windResistance: 1,
            activeWeaponAttackDamage: 'BBBBBB',
            activeWeaponAttackHit: 1,
            activeWeaponAttackType: 1,
            size: 1
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
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AdventureAttributes', async () => {
        const rxPromise = service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
