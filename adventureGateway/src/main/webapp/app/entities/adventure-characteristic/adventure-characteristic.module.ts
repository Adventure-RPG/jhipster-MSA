import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureCharacteristicComponent,
    AdventureCharacteristicDetailComponent,
    AdventureCharacteristicUpdateComponent,
    AdventureCharacteristicDeletePopupComponent,
    AdventureCharacteristicDeleteDialogComponent,
    adventureCharacteristicRoute,
    adventureCharacteristicPopupRoute
} from './';

const ENTITY_STATES = [...adventureCharacteristicRoute, ...adventureCharacteristicPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureCharacteristicComponent,
        AdventureCharacteristicDetailComponent,
        AdventureCharacteristicUpdateComponent,
        AdventureCharacteristicDeleteDialogComponent,
        AdventureCharacteristicDeletePopupComponent
    ],
    entryComponents: [
        AdventureCharacteristicComponent,
        AdventureCharacteristicUpdateComponent,
        AdventureCharacteristicDeleteDialogComponent,
        AdventureCharacteristicDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureCharacteristicModule {}
