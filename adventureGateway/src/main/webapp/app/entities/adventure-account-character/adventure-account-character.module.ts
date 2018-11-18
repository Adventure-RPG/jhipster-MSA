import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureAccountCharacterComponent,
    AdventureAccountCharacterDetailComponent,
    AdventureAccountCharacterUpdateComponent,
    AdventureAccountCharacterDeletePopupComponent,
    AdventureAccountCharacterDeleteDialogComponent,
    adventureAccountCharacterRoute,
    adventureAccountCharacterPopupRoute
} from './';

const ENTITY_STATES = [...adventureAccountCharacterRoute, ...adventureAccountCharacterPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureAccountCharacterComponent,
        AdventureAccountCharacterDetailComponent,
        AdventureAccountCharacterUpdateComponent,
        AdventureAccountCharacterDeleteDialogComponent,
        AdventureAccountCharacterDeletePopupComponent
    ],
    entryComponents: [
        AdventureAccountCharacterComponent,
        AdventureAccountCharacterUpdateComponent,
        AdventureAccountCharacterDeleteDialogComponent,
        AdventureAccountCharacterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureAccountCharacterModule {}
