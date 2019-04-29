import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';
import { AdventureAccountCharacterService } from './adventure-account-character.service';
import { AdventureAccountCharacterComponent } from './adventure-account-character.component';
import { AdventureAccountCharacterDetailComponent } from './adventure-account-character-detail.component';
import { AdventureAccountCharacterUpdateComponent } from './adventure-account-character-update.component';
import { AdventureAccountCharacterDeletePopupComponent } from './adventure-account-character-delete-dialog.component';
import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

@Injectable({ providedIn: 'root' })
export class AdventureAccountCharacterResolve implements Resolve<IAdventureAccountCharacter> {
    constructor(private service: AdventureAccountCharacterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureAccountCharacter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AdventureAccountCharacter>) => response.ok),
                map((adventureAccountCharacter: HttpResponse<AdventureAccountCharacter>) => adventureAccountCharacter.body)
            );
        }
        return of(new AdventureAccountCharacter());
    }
}

export const adventureAccountCharacterRoute: Routes = [
    {
        path: '',
        component: AdventureAccountCharacterComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adventureGatewayApp.adventureAccountCharacter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdventureAccountCharacterDetailComponent,
        resolve: {
            adventureAccountCharacter: AdventureAccountCharacterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAccountCharacter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdventureAccountCharacterUpdateComponent,
        resolve: {
            adventureAccountCharacter: AdventureAccountCharacterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAccountCharacter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdventureAccountCharacterUpdateComponent,
        resolve: {
            adventureAccountCharacter: AdventureAccountCharacterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAccountCharacter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adventureAccountCharacterPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdventureAccountCharacterDeletePopupComponent,
        resolve: {
            adventureAccountCharacter: AdventureAccountCharacterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adventureGatewayApp.adventureAccountCharacter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
