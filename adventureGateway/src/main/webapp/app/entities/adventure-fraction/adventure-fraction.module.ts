import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureFractionComponent,
    AdventureFractionDetailComponent,
    AdventureFractionUpdateComponent,
    AdventureFractionDeletePopupComponent,
    AdventureFractionDeleteDialogComponent,
    adventureFractionRoute,
    adventureFractionPopupRoute
} from './';

const ENTITY_STATES = [...adventureFractionRoute, ...adventureFractionPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureFractionComponent,
        AdventureFractionDetailComponent,
        AdventureFractionUpdateComponent,
        AdventureFractionDeleteDialogComponent,
        AdventureFractionDeletePopupComponent
    ],
    entryComponents: [
        AdventureFractionComponent,
        AdventureFractionUpdateComponent,
        AdventureFractionDeleteDialogComponent,
        AdventureFractionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureFractionModule {}
