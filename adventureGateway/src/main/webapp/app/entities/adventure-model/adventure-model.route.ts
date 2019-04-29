import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from './adventure-model.service';
import { AdventureModelComponent } from './adventure-model.component';
import { AdventureModelDetailComponent } from './adventure-model-detail.component';
import { AdventureModelUpdateComponent } from './adventure-model-update.component';
import { AdventureModelDeletePopupComponent } from './adventure-model-delete-dialog.component';
import { IAdventureModel } from 'app/shared/model/adventure-model.model';

@Injectable({ providedIn: 'root' })
export class AdventureModelResolve implements Resolve<IAdventureModel> {
    constructor(private service: AdventureModelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureModel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureModel>) => response.ok),
                map((adventureModel: HttpResponse<AdventureModel>) => adventureModel.body)
            );
        }
        return of(new AdventureModel());
    }
}

export const adventureModelRoute: Routes = [
    {
        path: '',
        component: AdventureModelComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdventureModelDetailComponent,
        resolve: {
            adventureModel: AdventureModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdventureModelUpdateComponent,
        resolve: {
            adventureModel: AdventureModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdventureModelUpdateComponent,
        resolve: {
            adventureModel: AdventureModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureModelPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdventureModelDeletePopupComponent,
        resolve: {
            adventureModel: AdventureModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
