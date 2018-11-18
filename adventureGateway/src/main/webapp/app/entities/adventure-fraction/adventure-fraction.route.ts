import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from './adventure-fraction.service';
import { AdventureFractionComponent } from './adventure-fraction.component';
import { AdventureFractionDetailComponent } from './adventure-fraction-detail.component';
import { AdventureFractionUpdateComponent } from './adventure-fraction-update.component';
import { AdventureFractionDeletePopupComponent } from './adventure-fraction-delete-dialog.component';
import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';

@Injectable({ providedIn: 'root' })
export class AdventureFractionResolve implements Resolve<IAdventureFraction> {
    constructor(private service: AdventureFractionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdventureFraction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureFraction>) => response.ok),
                map((adventureFraction: HttpResponse<AdventureFraction>) => adventureFraction.body)
            );
        }
        return of(new AdventureFraction());
    }
}

export const adventureFractionRoute: Routes = [
    {
        path: 'adventure-fraction',
        component: AdventureFractionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureFraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-fraction/:id/view',
        component: AdventureFractionDetailComponent,
        resolve: {
            adventureFraction: AdventureFractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureFraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-fraction/new',
        component: AdventureFractionUpdateComponent,
        resolve: {
            adventureFraction: AdventureFractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureFraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-fraction/:id/edit',
        component: AdventureFractionUpdateComponent,
        resolve: {
            adventureFraction: AdventureFractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureFraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureFractionPopupRoute: Routes = [
    {
        path: 'adventure-fraction/:id/delete',
        component: AdventureFractionDeletePopupComponent,
        resolve: {
            adventureFraction: AdventureFractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureFraction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
