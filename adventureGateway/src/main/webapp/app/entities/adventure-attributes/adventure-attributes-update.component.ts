import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAdventureAttributes, AdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from './adventure-attributes.service';

@Component({
  selector: 'jhi-adventure-attributes-update',
  templateUrl: './adventure-attributes-update.component.html'
})
export class AdventureAttributesUpdateComponent implements OnInit {
  adventureAttributes: IAdventureAttributes;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    defence: [null, [Validators.required]],
    defenceArmorType: [null, [Validators.required]],
    fireResistance: [null, [Validators.required]],
    earthResistance: [null, [Validators.required]],
    waterResistance: [null, [Validators.required]],
    windResistance: [null, [Validators.required]],
    activeWeaponAttackDamage: [null, [Validators.required]],
    activeWeaponAttackHit: [null, [Validators.required]],
    activeWeaponAttackType: [null, [Validators.required]],
    size: [null, [Validators.required]]
  });

  constructor(
    protected adventureAttributesService: AdventureAttributesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureAttributes }) => {
      this.updateForm(adventureAttributes);
      this.adventureAttributes = adventureAttributes;
    });
  }

  updateForm(adventureAttributes: IAdventureAttributes) {
    this.editForm.patchValue({
      id: adventureAttributes.id,
      defence: adventureAttributes.defence,
      defenceArmorType: adventureAttributes.defenceArmorType,
      fireResistance: adventureAttributes.fireResistance,
      earthResistance: adventureAttributes.earthResistance,
      waterResistance: adventureAttributes.waterResistance,
      windResistance: adventureAttributes.windResistance,
      activeWeaponAttackDamage: adventureAttributes.activeWeaponAttackDamage,
      activeWeaponAttackHit: adventureAttributes.activeWeaponAttackHit,
      activeWeaponAttackType: adventureAttributes.activeWeaponAttackType,
      size: adventureAttributes.size
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureAttributes = this.createFromForm();
    if (adventureAttributes.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureAttributesService.update(adventureAttributes));
    } else {
      this.subscribeToSaveResponse(this.adventureAttributesService.create(adventureAttributes));
    }
  }

  private createFromForm(): IAdventureAttributes {
    const entity = {
      ...new AdventureAttributes(),
      id: this.editForm.get(['id']).value,
      defence: this.editForm.get(['defence']).value,
      defenceArmorType: this.editForm.get(['defenceArmorType']).value,
      fireResistance: this.editForm.get(['fireResistance']).value,
      earthResistance: this.editForm.get(['earthResistance']).value,
      waterResistance: this.editForm.get(['waterResistance']).value,
      windResistance: this.editForm.get(['windResistance']).value,
      activeWeaponAttackDamage: this.editForm.get(['activeWeaponAttackDamage']).value,
      activeWeaponAttackHit: this.editForm.get(['activeWeaponAttackHit']).value,
      activeWeaponAttackType: this.editForm.get(['activeWeaponAttackType']).value,
      size: this.editForm.get(['size']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureAttributes>>) {
    result.subscribe((res: HttpResponse<IAdventureAttributes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
