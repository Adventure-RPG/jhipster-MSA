import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from './adventure-skill.service';
import { IAdventureScript } from 'app/shared/model/adventure-script.model';
import { AdventureScriptService } from 'app/entities/adventure-script';
import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';
import { AdventureAccountCharacterService } from 'app/entities/adventure-account-character';

@Component({
    selector: 'jhi-adventure-skill-update',
    templateUrl: './adventure-skill-update.component.html'
})
export class AdventureSkillUpdateComponent implements OnInit {
    adventureSkill: IAdventureSkill;
    isSaving: boolean;

    adventurescripts: IAdventureScript[];

    adventureaccountcharacters: IAdventureAccountCharacter[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected adventureSkillService: AdventureSkillService,
        protected adventureScriptService: AdventureScriptService,
        protected adventureAccountCharacterService: AdventureAccountCharacterService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureSkill }) => {
            this.adventureSkill = adventureSkill;
        });
        this.adventureScriptService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureScript[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureScript[]>) => response.body)
            )
            .subscribe((res: IAdventureScript[]) => (this.adventurescripts = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.adventureAccountCharacterService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureAccountCharacter[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureAccountCharacter[]>) => response.body)
            )
            .subscribe(
                (res: IAdventureAccountCharacter[]) => (this.adventureaccountcharacters = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureSkill.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureSkillService.update(this.adventureSkill));
        } else {
            this.subscribeToSaveResponse(this.adventureSkillService.create(this.adventureSkill));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureSkill>>) {
        result.subscribe((res: HttpResponse<IAdventureSkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureScriptById(index: number, item: IAdventureScript) {
        return item.id;
    }

    trackAdventureAccountCharacterById(index: number, item: IAdventureAccountCharacter) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
