import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureModelOptions } from 'app/shared/model/adventure-model-options.model';
import { AdventureModelOptionsService } from './adventure-model-options.service';
import { AdventureModelOptionsComponent } from './adventure-model-options.component';
import { AdventureModelOptionsDetailComponent } from './adventure-model-options-detail.component';
import { AdventureModelOptionsUpdateComponent } from './adventure-model-options-update.component';
import { AdventureModelOptionsDeletePopupComponent } from './adventure-model-options-delete-dialog.component';
import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';

@Injectable({ providedIn: 'root' })
export class AdventureModelOptionsResolve implements Resolve<IAdventureModelOptions> {
    constructor(private service: AdventureModelOptionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureModelOptions> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureModelOptions>) => response.ok),
                map((adventureModelOptions: HttpResponse<AdventureModelOptions>) => adventureModelOptions.body)
            );
        }
        return of(new AdventureModelOptions());
    }
}

export const adventureModelOptionsRoute: Routes = [
    {
        path: '',
        component: AdventureModelOptionsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureModelOptions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdventureModelOptionsDetailComponent,
        resolve: {
            adventureModelOptions: AdventureModelOptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModelOptions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdventureModelOptionsUpdateComponent,
        resolve: {
            adventureModelOptions: AdventureModelOptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModelOptions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdventureModelOptionsUpdateComponent,
        resolve: {
            adventureModelOptions: AdventureModelOptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModelOptions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureModelOptionsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdventureModelOptionsDeletePopupComponent,
        resolve: {
            adventureModelOptions: AdventureModelOptionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureModelOptions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
