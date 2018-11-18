import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';
import { AdventureSkillService } from './adventure-skill.service';
import { IAdventureScript } from 'app/shared/model/adventure-script.model';
import { AdventureScriptService } from 'app/entities/adventure-script';

@Component({
    selector: 'jhi-adventure-skill-update',
    templateUrl: './adventure-skill-update.component.html'
})
export class AdventureSkillUpdateComponent implements OnInit {
    adventureSkill: IAdventureSkill;
    isSaving: boolean;

    adventurescripts: IAdventureScript[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adventureSkillService: AdventureSkillService,
        private adventureScriptService: AdventureScriptService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureSkill }) => {
            this.adventureSkill = adventureSkill;
        });
        this.adventureScriptService.query().subscribe(
            (res: HttpResponse<IAdventureScript[]>) => {
                this.adventurescripts = res.body;
            },
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureSkill>>) {
        result.subscribe((res: HttpResponse<IAdventureSkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureScriptById(index: number, item: IAdventureScript) {
        return item.id;
    }
}
