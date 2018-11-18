import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureScript } from 'app/shared/model/adventure-script.model';
import { AdventureScriptService } from './adventure-script.service';
import { AdventureScriptComponent } from './adventure-script.component';
import { AdventureScriptDetailComponent } from './adventure-script-detail.component';
import { AdventureScriptUpdateComponent } from './adventure-script-update.component';
import { AdventureScriptDeletePopupComponent } from './adventure-script-delete-dialog.component';
import { IAdventureScript } from 'app/shared/model/adventure-script.model';

@Injectable({ providedIn: 'root' })
export class AdventureScriptResolve implements Resolve<IAdventureScript> {
    constructor(private service: AdventureScriptService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdventureScript> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureScript>) => response.ok),
                map((adventureScript: HttpResponse<AdventureScript>) => adventureScript.body)
            );
        }
        return of(new AdventureScript());
    }
}

export const adventureScriptRoute: Routes = [
    {
        path: 'adventure-script',
        component: AdventureScriptComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureScript.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-script/:id/view',
        component: AdventureScriptDetailComponent,
        resolve: {
            adventureScript: AdventureScriptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureScript.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-script/new',
        component: AdventureScriptUpdateComponent,
        resolve: {
            adventureScript: AdventureScriptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureScript.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-script/:id/edit',
        component: AdventureScriptUpdateComponent,
        resolve: {
            adventureScript: AdventureScriptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureScript.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureScriptPopupRoute: Routes = [
    {
        path: 'adventure-script/:id/delete',
        component: AdventureScriptDeletePopupComponent,
        resolve: {
            adventureScript: AdventureScriptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureScript.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
