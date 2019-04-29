import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';
import { AdventureInventoryCharService } from './adventure-inventory-char.service';
import { AdventureInventoryCharComponent } from './adventure-inventory-char.component';
import { AdventureInventoryCharDetailComponent } from './adventure-inventory-char-detail.component';
import { AdventureInventoryCharUpdateComponent } from './adventure-inventory-char-update.component';
import { AdventureInventoryCharDeletePopupComponent } from './adventure-inventory-char-delete-dialog.component';
import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';

@Injectable({ providedIn: 'root' })
export class AdventureInventoryCharResolve implements Resolve<IAdventureInventoryChar> {
    constructor(private service: AdventureInventoryCharService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureInventoryChar> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureInventoryChar>) => response.ok),
                map((adventureInventoryChar: HttpResponse<AdventureInventoryChar>) => adventureInventoryChar.body)
            );
        }
        return of(new AdventureInventoryChar());
    }
}

export const adventureInventoryCharRoute: Routes = [
    {
        path: '',
        component: AdventureInventoryCharComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureInventoryChar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdventureInventoryCharDetailComponent,
        resolve: {
            adventureInventoryChar: AdventureInventoryCharResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureInventoryChar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdventureInventoryCharUpdateComponent,
        resolve: {
            adventureInventoryChar: AdventureInventoryCharResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureInventoryChar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdventureInventoryCharUpdateComponent,
        resolve: {
            adventureInventoryChar: AdventureInventoryCharResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureInventoryChar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureInventoryCharPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdventureInventoryCharDeletePopupComponent,
        resolve: {
            adventureInventoryChar: AdventureInventoryCharResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureInventoryChar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
