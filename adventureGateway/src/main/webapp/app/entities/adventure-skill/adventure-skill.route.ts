import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from './adventure-skill.service';
import { AdventureSkillComponent } from './adventure-skill.component';
import { AdventureSkillDetailComponent } from './adventure-skill-detail.component';
import { AdventureSkillUpdateComponent } from './adventure-skill-update.component';
import { AdventureSkillDeletePopupComponent } from './adventure-skill-delete-dialog.component';
import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';

@Injectable({ providedIn: 'root' })
export class AdventureSkillResolve implements Resolve<IAdventureSkill> {
  constructor(private service: AdventureSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdventureSkill> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AdventureSkill>) => response.ok),
        map((adventureSkill: HttpResponse<AdventureSkill>) => adventureSkill.body)
      );
    }
    return of(new AdventureSkill());
  }
}

export const adventureSkillRoute: Routes = [
  {
    path: '',
    component: AdventureSkillComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'adventureGatewayApp.adventureSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdventureSkillDetailComponent,
    resolve: {
      adventureSkill: AdventureSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdventureSkillUpdateComponent,
    resolve: {
      adventureSkill: AdventureSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdventureSkillUpdateComponent,
    resolve: {
      adventureSkill: AdventureSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adventureSkillPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdventureSkillDeletePopupComponent,
    resolve: {
      adventureSkill: AdventureSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'adventureGatewayApp.adventureSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
