import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureSkill, AdventureSkill } from 'app/shared/model/adventure-skill.model';
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

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    position: [],
    adventureScriptId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected adventureSkillService: AdventureSkillService,
    protected adventureScriptService: AdventureScriptService,
    protected adventureAccountCharacterService: AdventureAccountCharacterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureSkill }) => {
      this.updateForm(adventureSkill);
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

  updateForm(adventureSkill: IAdventureSkill) {
    this.editForm.patchValue({
      id: adventureSkill.id,
      name: adventureSkill.name,
      position: adventureSkill.position,
      adventureScriptId: adventureSkill.adventureScriptId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureSkill = this.createFromForm();
    if (adventureSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureSkillService.update(adventureSkill));
    } else {
      this.subscribeToSaveResponse(this.adventureSkillService.create(adventureSkill));
    }
  }

  private createFromForm(): IAdventureSkill {
    const entity = {
      ...new AdventureSkill(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      position: this.editForm.get(['position']).value,
      adventureScriptId: this.editForm.get(['adventureScriptId']).value
    };
    return entity;
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
