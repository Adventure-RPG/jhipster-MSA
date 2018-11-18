import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from './adventure-category-type.service';
import { AdventureCategoryTypeComponent } from './adventure-category-type.component';
import { AdventureCategoryTypeDetailComponent } from './adventure-category-type-detail.component';
import { AdventureCategoryTypeUpdateComponent } from './adventure-category-type-update.component';
import { AdventureCategoryTypeDeletePopupComponent } from './adventure-category-type-delete-dialog.component';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';

@Injectable({ providedIn: 'root' })
export class AdventureCategoryTypeResolve implements Resolve<IAdventureCategoryType> {
    constructor(private service: AdventureCategoryTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdventureCategoryType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureCategoryType>) => response.ok),
                map((adventureCategoryType: HttpResponse<AdventureCategoryType>) => adventureCategoryType.body)
            );
        }
        return of(new AdventureCategoryType());
    }
}

export const adventureCategoryTypeRoute: Routes = [
    {
        path: 'adventure-category-type',
        component: AdventureCategoryTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureCategoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-category-type/:id/view',
        component: AdventureCategoryTypeDetailComponent,
        resolve: {
            adventureCategoryType: AdventureCategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCategoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-category-type/new',
        component: AdventureCategoryTypeUpdateComponent,
        resolve: {
            adventureCategoryType: AdventureCategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCategoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-category-type/:id/edit',
        component: AdventureCategoryTypeUpdateComponent,
        resolve: {
            adventureCategoryType: AdventureCategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCategoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureCategoryTypePopupRoute: Routes = [
    {
        path: 'adventure-category-type/:id/delete',
        component: AdventureCategoryTypeDeletePopupComponent,
        resolve: {
            adventureCategoryType: AdventureCategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureCategoryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
