import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdventureGatewaySharedModule } from 'app/shared';
import {
    AdventureSkillComponent,
    AdventureSkillDetailComponent,
    AdventureSkillUpdateComponent,
    AdventureSkillDeletePopupComponent,
    AdventureSkillDeleteDialogComponent,
    adventureSkillRoute,
    adventureSkillPopupRoute
} from './';

const ENTITY_STATES = [...adventureSkillRoute, ...adventureSkillPopupRoute];

@NgModule({
    imports: [AdventureGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdventureSkillComponent,
        AdventureSkillDetailComponent,
        AdventureSkillUpdateComponent,
        AdventureSkillDeleteDialogComponent,
        AdventureSkillDeletePopupComponent
    ],
    entryComponents: [
        AdventureSkillComponent,
        AdventureSkillUpdateComponent,
        AdventureSkillDeleteDialogComponent,
        AdventureSkillDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdventureGatewayAdventureSkillModule {}
