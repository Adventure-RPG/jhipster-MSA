import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureCategoryTypeComponent,
    AdventureCategoryTypeDetailComponent,
    AdventureCategoryTypeUpdateComponent,
    AdventureCategoryTypeDeletePopupComponent,
    AdventureCategoryTypeDeleteDialogComponent,
    adventureCategoryTypeRoute,
    adventureCategoryTypePopupRoute
} from './';

const ENTITY_STATES = [...adventureCategoryTypeRoute, ...adventureCategoryTypePopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureCategoryTypeComponent,
        AdventureCategoryTypeDetailComponent,
        AdventureCategoryTypeUpdateComponent,
        AdventureCategoryTypeDeleteDialogComponent,
        AdventureCategoryTypeDeletePopupComponent
    ],
    entryComponents: [
        AdventureCategoryTypeComponent,
        AdventureCategoryTypeUpdateComponent,
        AdventureCategoryTypeDeleteDialogComponent,
        AdventureCategoryTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureCategoryTypeModule {}
