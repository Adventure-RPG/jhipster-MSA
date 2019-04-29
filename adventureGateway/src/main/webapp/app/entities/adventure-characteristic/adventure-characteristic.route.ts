import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';
import { AdventureCharacteristicService } from './adventure-characteristic.service';
import { AdventureCharacteristicComponent } from './adventure-characteristic.component';
import { AdventureCharacteristicDetailComponent } from './adventure-characteristic-detail.component';
import { AdventureCharacteristicUpdateComponent } from './adventure-characteristic-update.component';
import { AdventureCharacteristicDeletePopupComponent } from './adventure-characteristic-delete-dialog.component';
import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

@Injectable({ providedIn: 'root' })
export class AdventureCharacteristicResolve implements Resolve<IAdventureCharacteristic> {
    constructor(private service: AdventureCharacteristicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureCharacteristic> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureCharacteristic>) => response.ok),
                map((adventureCharacteristic: HttpResponse<AdventureCharacteristic>) => adventureCharacteristic.body)
            );
        }
        return of(new AdventureCharacteristic());
    }
}

export const adventureCharacteristicRoute: Routes = [
    {
        path: '',
        component: AdventureCharacteristicComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdventureCharacteristicDetailComponent,
        resolve: {
            adventureCharacteristic: AdventureCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdventureCharacteristicUpdateComponent,
        resolve: {
            adventureCharacteristic: AdventureCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdventureCharacteristicUpdateComponent,
        resolve: {
            adventureCharacteristic: AdventureCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureCharacteristicPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdventureCharacteristicDeletePopupComponent,
        resolve: {
            adventureCharacteristic: AdventureCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
