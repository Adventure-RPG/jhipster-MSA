import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from './adventure-race-options.service';
import { AdventureRaceOptionsComponent } from './adventure-race-options.component';
import { AdventureRaceOptionsDetailComponent } from './adventure-race-options-detail.component';
import { AdventureRaceOptionsUpdateComponent } from './adventure-race-options-update.component';
import { AdventureRaceOptionsDeletePopupComponent } from './adventure-race-options-delete-dialog.component';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

@Injectable({ providedIn: 'root' })
export class AdventureRaceOptionsResolve implements Resolve<IAdventureRaceOptions> {
  constructor(private service: AdventureRaceOptionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureRaceOptions> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AdventureRaceOptions>) => response.ok),
        map((adventureRaceOptions: HttpResponse<AdventureRaceOptions>) => adventureRaceOptions.body)
      );
    }
    return of(new AdventureRaceOptions());
  }
}

export const adventureRaceOptionsRoute: Routes = [
  {
    path: '',
    component: AdventureRaceOptionsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'adventureGatewayApp.adventureRaceOptions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdventureRaceOptionsDetailComponent,
    resolve: {
      adventureRaceOptions: AdventureRaceOptionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureRaceOptions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdventureRaceOptionsUpdateComponent,
    resolve: {
      adventureRaceOptions: AdventureRaceOptionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureRaceOptions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdventureRaceOptionsUpdateComponent,
    resolve: {
      adventureRaceOptions: AdventureRaceOptionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureRaceOptions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adventureRaceOptionsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdventureRaceOptionsDeletePopupComponent,
    resolve: {
      adventureRaceOptions: AdventureRaceOptionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureRaceOptions.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
