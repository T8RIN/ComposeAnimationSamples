package com.t8rin.animation.samples.ui.components.model

import com.t8rin.animation.samples.R

sealed class Question(
    val questionResId: Int,
    val yesPath: Question? = null,
    val noPath: Question? = null,
    val animationType: AnimationType? = null
) {
    data object Start : Question(
        questionResId = R.string.is_need_to_be_repeated,
        yesPath = RememberInfiniteTransition,
        noPath = IsLayout
    )

    data object RememberInfiniteTransition : Question(
        questionResId = R.string.infinite_transition,
        animationType = AnimationType.InfiniteTransition
    )

    data object IsLayout : Question(
        questionResId = R.string.is_layout_animation,
        yesPath = IsSwitchBetweenComposables,
        noPath = IsAnimateMultiple
    )

    data object IsSwitchBetweenComposables : Question(
        questionResId = R.string.is_content_switching,
        yesPath = AnimatedContentAnimation,
        noPath = IsAppearanceAnimation
    )

    data object AnimatedContentAnimation : Question(
        questionResId = R.string.animated_content,
        animationType = AnimationType.AnimatedContent
    )

    data object IsAppearanceAnimation : Question(
        questionResId = R.string.is_appearance_animation,
        yesPath = AnimateVisibility,
        noPath = IsNeedAnimateContentSize
    )

    data object IsNeedAnimateContentSize : Question(
        questionResId = R.string.is_need_size,
        yesPath = ContentSize,
        noPath = IsOtherProperties
    )

    data object IsOtherProperties : Question(
        questionResId = R.string.is_other_params,
        yesPath = IsIndependentProperties,
        noPath = IsListItemAnimations
    )

    data object IsListItemAnimations : Question(
        questionResId = R.string.is_list_item,
        yesPath = AnimateItemPlacement,
        noPath = AnswerNotFound
    )

    data object AnimateItemPlacement : Question(
        questionResId = R.string.animate_item,
        animationType = AnimationType.AnimateItemPlacement
    )

    data object AnimateVisibility : Question(
        questionResId = R.string.animated_visibility,
        animationType = AnimationType.AnimatedVisibility
    )

    data object ContentSize : Question(
        questionResId = R.string.animate_content_size,
        animationType = AnimationType.AnimateContentSize
    )

    data object IsAnimateMultiple : Question(
        questionResId = R.string.is_multiple,
        yesPath = IsIndependentProperties,
        noPath = IsHavePredefinedValues
    )

    data object IsHavePredefinedValues : Question(
        questionResId = R.string.is_predefined,
        yesPath = AnimateAsState,
        noPath = IsGestureDrivenAnimation
    )

    data object AnimateAsState : Question(
        questionResId = R.string.animate_as_state,
        animationType = AnimationType.AnimateAsState
    )

    data object IsIndependentProperties : Question(
        questionResId = R.string.is_independent,
        yesPath = AnimateAsState,
        noPath = IsAnimateTogether
    )

    data object IsAnimateTogether : Question(
        questionResId = R.string.is_together,
        yesPath = UpdateTransition,
        noPath = AnimateSuspend
    )

    data object UpdateTransition : Question(
        questionResId = R.string.update_transition,
        animationType = AnimationType.UpdateTransition
    )

    data object AnimateSuspend : Question(
        questionResId = R.string.suspend_animatable,
        animationType = AnimationType.Animatable
    )

    data object IsGestureDrivenAnimation : Question(
        questionResId = R.string.is_source_of_truth,
        yesPath = AnimateToSnap,
        noPath = IsOneShotAnimation
    )

    data object AnimateToSnap : Question(
        questionResId = R.string.animatable,
        animationType = AnimationType.Animatable
    )

    data object IsOneShotAnimation : Question(
        questionResId = R.string.is_one_shot,
        yesPath = AnimationState,
        noPath = AnswerNotFound
    )

    data object AnimationState : Question(
        questionResId = R.string.animation_state,
        animationType = AnimationType.AnimationState
    )

    data object AnswerNotFound : Question(
        questionResId = R.string.no_answer
    )
}