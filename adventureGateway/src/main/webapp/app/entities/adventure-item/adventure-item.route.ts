import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from './adventure-item.service';
import { AdventureItemComponent } from './adventure-item.component';
import { AdventureItemDetailComponent } from './adventure-item-detail.component';
import { AdventureItemUpdateComponent } from './adventure-item-update.component';
import { AdventureItemDeletePopupComponent } from './adventure-item-delete-dialog.component';
import { IAdventureItem } from 'app/shared/model/adventure-item.model';

@Injectable({ providedIn: 'root' })
export class AdventureItemResolve implements Resolve<IAdventureItem> {
  constructor(private service: AdventureItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AdventureItem>) => response.ok),
        map((adventureItem: HttpResponse<AdventureItem>) => adventureItem.body)
      );
    }
    return of(new AdventureItem());
  }
}

export const adventureItemRoute: Routes = [
  {
    path: '',
    component: AdventureItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'adventureGatewayApp.adventureItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdventureItemDetailComponent,
    resolve: {
      adventureItem: AdventureItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdventureItemUpdateComponent,
    resolve: {
      adventureItem: AdventureItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdventureItemUpdateComponent,
    resolve: {
      adventureItem: AdventureItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adventureItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdventureItemDeletePopupComponent,
    resolve: {
      adventureItem: AdventureItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
