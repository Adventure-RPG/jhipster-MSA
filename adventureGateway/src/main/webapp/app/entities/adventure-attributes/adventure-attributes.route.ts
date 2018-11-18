import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from './adventure-attributes.service';
import { AdventureAttributesComponent } from './adventure-attributes.component';
import { AdventureAttributesDetailComponent } from './adventure-attributes-detail.component';
import { AdventureAttributesUpdateComponent } from './adventure-attributes-update.component';
import { AdventureAttributesDeletePopupComponent } from './adventure-attributes-delete-dialog.component';
import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';

@Injectable({ providedIn: 'root' })
export class AdventureAttributesResolve implements Resolve<IAdventureAttributes> {
    constructor(private service: AdventureAttributesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdventureAttributes> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureAttributes>) => response.ok),
                map((adventureAttributes: HttpResponse<AdventureAttributes>) => adventureAttributes.body)
            );
        }
        return of(new AdventureAttributes());
    }
}

export const adventureAttributesRoute: Routes = [
    {
        path: 'adventure-attributes',
        component: AdventureAttributesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-attributes/:id/view',
        component: AdventureAttributesDetailComponent,
        resolve: {
            adventureAttributes: AdventureAttributesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-attributes/new',
        component: AdventureAttributesUpdateComponent,
        resolve: {
            adventureAttributes: AdventureAttributesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-attributes/:id/edit',
        component: AdventureAttributesUpdateComponent,
        resolve: {
            adventureAttributes: AdventureAttributesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureAttributesPopupRoute: Routes = [
    {
        path: 'adventure-attributes/:id/delete',
        component: AdventureAttributesDeletePopupComponent,
        resolve: {
            adventureAttributes: AdventureAttributesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
