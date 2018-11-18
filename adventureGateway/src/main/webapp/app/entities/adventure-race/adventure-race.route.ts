import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from './adventure-race.service';
import { AdventureRaceComponent } from './adventure-race.component';
import { AdventureRaceDetailComponent } from './adventure-race-detail.component';
import { AdventureRaceUpdateComponent } from './adventure-race-update.component';
import { AdventureRaceDeletePopupComponent } from './adventure-race-delete-dialog.component';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';

@Injectable({ providedIn: 'root' })
export class AdventureRaceResolve implements Resolve<IAdventureRace> {
    constructor(private service: AdventureRaceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AdventureRace> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureRace>) => response.ok),
                map((adventureRace: HttpResponse<AdventureRace>) => adventureRace.body)
            );
        }
        return of(new AdventureRace());
    }
}

export const adventureRaceRoute: Routes = [
    {
        path: 'adventure-race',
        component: AdventureRaceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureRace.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-race/:id/view',
        component: AdventureRaceDetailComponent,
        resolve: {
            adventureRace: AdventureRaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureRace.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-race/new',
        component: AdventureRaceUpdateComponent,
        resolve: {
            adventureRace: AdventureRaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureRace.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adventure-race/:id/edit',
        component: AdventureRaceUpdateComponent,
        resolve: {
            adventureRace: AdventureRaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureRace.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureRacePopupRoute: Routes = [
    {
        path: 'adventure-race/:id/delete',
        component: AdventureRaceDeletePopupComponent,
        resolve: {
            adventureRace: AdventureRaceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureRace.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
