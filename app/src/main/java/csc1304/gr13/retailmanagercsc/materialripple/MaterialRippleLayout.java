package csc1304.gr13.retailmanagercsc.materialripple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;

public class MaterialRippleLayout extends FrameLayout {
    private static final float DEFAULT_ALPHA = 0.2f;
    private static final int DEFAULT_BACKGROUND = 0;
    private static final int DEFAULT_COLOR = -16777216;
    private static final boolean DEFAULT_DELAY_CLICK = true;
    private static final float DEFAULT_DIAMETER_DP = 35.0f;
    private static final int DEFAULT_DURATION = 350;
    private static final int DEFAULT_FADE_DURATION = 75;
    private static final boolean DEFAULT_HOVER = true;
    private static final boolean DEFAULT_PERSISTENT = false;
    private static final boolean DEFAULT_RIPPLE_OVERLAY = false;
    private static final int DEFAULT_ROUNDED_CORNERS = 0;
    private static final boolean DEFAULT_SEARCH_ADAPTER = false;
    private static final int FADE_EXTRA_DELAY = 50;
    private static final long HOVER_DURATION = 2500;
    private final Rect bounds;
    /* access modifiers changed from: private */
    public View childView;
    private Property<MaterialRippleLayout, Integer> circleAlphaProperty;
    private Point currentCoords;
    private boolean eventCancelled;
    private GestureDetector gestureDetector;
    /* access modifiers changed from: private */
    public boolean hasPerformedLongPress;
    private ObjectAnimator hoverAnimator;
    private int layerType;
    private SimpleOnGestureListener longClickListener;
    private final Paint paint;
    private AdapterView parentAdapter;
    private PerformClickEvent pendingClickEvent;
    private PressedEvent pendingPressEvent;
    private int positionInAdapter;
    /* access modifiers changed from: private */
    public boolean prepressed;
    private Point previousCoords;
    private float radius;
    private Property<MaterialRippleLayout, Float> radiusProperty;
    /* access modifiers changed from: private */
    public int rippleAlpha;
    private AnimatorSet rippleAnimator;
    private Drawable rippleBackground;
    private int rippleColor;
    /* access modifiers changed from: private */
    public boolean rippleDelayClick;
    private int rippleDiameter;
    private int rippleDuration;
    private int rippleFadeDuration;
    /* access modifiers changed from: private */
    public boolean rippleHover;
    /* access modifiers changed from: private */
    public boolean rippleInAdapter;
    private boolean rippleOverlay;
    /* access modifiers changed from: private */
    public boolean ripplePersistent;
    private float rippleRoundedCorners;

    private class PerformClickEvent implements Runnable {
        private PerformClickEvent() {
        }

        public void run() {
            if (!MaterialRippleLayout.this.hasPerformedLongPress) {
                if (MaterialRippleLayout.this.getParent() instanceof AdapterView) {
                    clickAdapterView((AdapterView) MaterialRippleLayout.this.getParent());
                } else if (MaterialRippleLayout.this.rippleInAdapter) {
                    clickAdapterView(MaterialRippleLayout.this.findParentAdapterView());
                } else {
                    MaterialRippleLayout.this.childView.performClick();
                }
            }
        }

        private void clickAdapterView(AdapterView adapterView) {
            int positionForView = adapterView.getPositionForView(MaterialRippleLayout.this);
            long itemId = adapterView.getAdapter() != null ? adapterView.getAdapter().getItemId(positionForView) : 0;
            if (positionForView != -1) {
                adapterView.performItemClick(MaterialRippleLayout.this, positionForView, itemId);
            }
        }
    }

    private final class PressedEvent implements Runnable {
        private final MotionEvent event;

        public PressedEvent(MotionEvent motionEvent) {
            this.event = motionEvent;
        }

        public void run() {
            MaterialRippleLayout.this.prepressed = false;
            MaterialRippleLayout.this.childView.setLongClickable(false);
            MaterialRippleLayout.this.childView.onTouchEvent(this.event);
            MaterialRippleLayout.this.childView.setPressed(true);
            if (MaterialRippleLayout.this.rippleHover) {
                MaterialRippleLayout.this.startHover();
            }
        }
    }

    public static class RippleBuilder {
        private final View child;
        private final Context context;
        private float rippleAlpha = MaterialRippleLayout.DEFAULT_ALPHA;
        private int rippleBackground = 0;
        private int rippleColor = -16777216;
        private boolean rippleDelayClick = true;
        private float rippleDiameter = MaterialRippleLayout.DEFAULT_DIAMETER_DP;
        private int rippleDuration = MaterialRippleLayout.DEFAULT_DURATION;
        private int rippleFadeDuration = 75;
        private boolean rippleHover = true;
        private boolean rippleOverlay = false;
        private boolean ripplePersistent = false;
        private float rippleRoundedCorner = 0.0f;
        private boolean rippleSearchAdapter = false;

        public RippleBuilder(View view) {
            this.child = view;
            this.context = view.getContext();
        }

        public RippleBuilder rippleColor(int i) {
            this.rippleColor = i;
            return this;
        }

        public RippleBuilder rippleOverlay(boolean z) {
            this.rippleOverlay = z;
            return this;
        }

        public RippleBuilder rippleHover(boolean z) {
            this.rippleHover = z;
            return this;
        }

        public RippleBuilder rippleDiameterDp(int i) {
            this.rippleDiameter = (float) i;
            return this;
        }

        public RippleBuilder rippleDuration(int i) {
            this.rippleDuration = i;
            return this;
        }

        public RippleBuilder rippleAlpha(float f) {
            this.rippleAlpha = f * 255.0f;
            return this;
        }

        public RippleBuilder rippleDelayClick(boolean z) {
            this.rippleDelayClick = z;
            return this;
        }

        public RippleBuilder rippleFadeDuration(int i) {
            this.rippleFadeDuration = i;
            return this;
        }

        public RippleBuilder ripplePersistent(boolean z) {
            this.ripplePersistent = z;
            return this;
        }

        public RippleBuilder rippleBackground(int i) {
            this.rippleBackground = i;
            return this;
        }

        public RippleBuilder rippleInAdapter(boolean z) {
            this.rippleSearchAdapter = z;
            return this;
        }

        public RippleBuilder rippleRoundedCorners(int i) {
            this.rippleRoundedCorner = (float) i;
            return this;
        }

        public MaterialRippleLayout create() {
            int i;
            MaterialRippleLayout materialRippleLayout = new MaterialRippleLayout(this.context);
            materialRippleLayout.setRippleColor(this.rippleColor);
            materialRippleLayout.setDefaultRippleAlpha((int) this.rippleAlpha);
            materialRippleLayout.setRippleDelayClick(this.rippleDelayClick);
            materialRippleLayout.setRippleDiameter((int) MaterialRippleLayout.dpToPx(this.context.getResources(), this.rippleDiameter));
            materialRippleLayout.setRippleDuration(this.rippleDuration);
            materialRippleLayout.setRippleFadeDuration(this.rippleFadeDuration);
            materialRippleLayout.setRippleHover(this.rippleHover);
            materialRippleLayout.setRipplePersistent(this.ripplePersistent);
            materialRippleLayout.setRippleOverlay(this.rippleOverlay);
            materialRippleLayout.setRippleBackground(this.rippleBackground);
            materialRippleLayout.setRippleInAdapter(this.rippleSearchAdapter);
            materialRippleLayout.setRippleRoundedCorners((int) MaterialRippleLayout.dpToPx(this.context.getResources(), this.rippleRoundedCorner));
            LayoutParams layoutParams = (LayoutParams) this.child.getLayoutParams();
            ViewGroup viewGroup = (ViewGroup) this.child.getParent();
            if (viewGroup == null || !(viewGroup instanceof MaterialRippleLayout)) {
                if (viewGroup != null) {
                    i = viewGroup.indexOfChild(this.child);
                    viewGroup.removeView(this.child);
                } else {
                    i = 0;
                }
                materialRippleLayout.addView(this.child, new LayoutParams(-1, -1));
                if (viewGroup != null) {
                    viewGroup.addView(materialRippleLayout, i, layoutParams);
                }
                return materialRippleLayout;
            }
            throw new IllegalStateException("MaterialRippleLayout could not be created: parent of the view already is a MaterialRippleLayout");
        }
    }

    public boolean isInEditMode() {
        return true;
    }

    /* renamed from: on */
    public static RippleBuilder m14on(View view) {
        return new RippleBuilder(view);
    }

    public MaterialRippleLayout(Context context) {
        this(context, null, 0);
    }

    public MaterialRippleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialRippleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint(1);
        this.bounds = new Rect();
        this.currentCoords = new Point();
        this.previousCoords = new Point();
        this.longClickListener = new SimpleOnGestureListener() {
            public void onLongPress(MotionEvent motionEvent) {
                MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.this;
                materialRippleLayout.hasPerformedLongPress = materialRippleLayout.childView.performLongClick();
                if (MaterialRippleLayout.this.hasPerformedLongPress) {
                    if (MaterialRippleLayout.this.rippleHover) {
                        MaterialRippleLayout.this.startRipple(null);
                    }
                    MaterialRippleLayout.this.cancelPressedEvent();
                }
            }

            public boolean onDown(MotionEvent motionEvent) {
                MaterialRippleLayout.this.hasPerformedLongPress = false;
                return super.onDown(motionEvent);
            }
        };
        this.radiusProperty = new Property<MaterialRippleLayout, Float>(Float.class, "radius") {
            public Float get(MaterialRippleLayout materialRippleLayout) {
                return Float.valueOf(materialRippleLayout.getRadius());
            }

            public void set(MaterialRippleLayout materialRippleLayout, Float f) {
                materialRippleLayout.setRadius(f.floatValue());
            }
        };
        this.circleAlphaProperty = new Property<MaterialRippleLayout, Integer>(Integer.class, "rippleAlpha") {
            public Integer get(MaterialRippleLayout materialRippleLayout) {
                return Integer.valueOf(materialRippleLayout.getRippleAlpha());
            }

            public void set(MaterialRippleLayout materialRippleLayout, Integer num) {
                materialRippleLayout.setRippleAlpha(num);
            }
        };
        setWillNotDraw(false);
        this.gestureDetector = new GestureDetector(context, this.longClickListener);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0584R.styleable.MaterialRippleLayout);
        this.rippleColor = obtainStyledAttributes.getColor(C0584R.styleable.MaterialRippleLayout_mrl_rippleColor, -16777216);
        this.rippleDiameter = obtainStyledAttributes.getDimensionPixelSize(C0584R.styleable.MaterialRippleLayout_mrl_rippleDimension, (int) dpToPx(getResources(), DEFAULT_DIAMETER_DP));
        this.rippleOverlay = obtainStyledAttributes.getBoolean(C0584R.styleable.MaterialRippleLayout_mrl_rippleOverlay, false);
        this.rippleHover = obtainStyledAttributes.getBoolean(C0584R.styleable.MaterialRippleLayout_mrl_rippleHover, true);
        this.rippleDuration = obtainStyledAttributes.getInt(C0584R.styleable.MaterialRippleLayout_mrl_rippleDuration, DEFAULT_DURATION);
        this.rippleAlpha = (int) (obtainStyledAttributes.getFloat(C0584R.styleable.MaterialRippleLayout_mrl_rippleAlpha, DEFAULT_ALPHA) * 255.0f);
        this.rippleDelayClick = obtainStyledAttributes.getBoolean(C0584R.styleable.MaterialRippleLayout_mrl_rippleDelayClick, true);
        this.rippleFadeDuration = obtainStyledAttributes.getInteger(C0584R.styleable.MaterialRippleLayout_mrl_rippleFadeDuration, 75);
        this.rippleBackground = new ColorDrawable(obtainStyledAttributes.getColor(C0584R.styleable.MaterialRippleLayout_mrl_rippleBackground, 0));
        this.ripplePersistent = obtainStyledAttributes.getBoolean(C0584R.styleable.MaterialRippleLayout_mrl_ripplePersistent, false);
        this.rippleInAdapter = obtainStyledAttributes.getBoolean(C0584R.styleable.MaterialRippleLayout_mrl_rippleInAdapter, false);
        this.rippleRoundedCorners = (float) obtainStyledAttributes.getDimensionPixelSize(C0584R.styleable.MaterialRippleLayout_mrl_rippleRoundedCorners, 0);
        obtainStyledAttributes.recycle();
        this.paint.setColor(this.rippleColor);
        this.paint.setAlpha(this.rippleAlpha);
        enableClipPathSupportIfNecessary();
    }

    public <T extends View> T getChildView() {
        return (T) this.childView;
    }

    public final void addView(View view, int i, LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            this.childView = view;
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException("MaterialRippleLayout can host only one child");
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        View view = this.childView;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            return;
        }
        throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        View view = this.childView;
        if (view != null) {
            view.setOnLongClickListener(onLongClickListener);
            return;
        }
        throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !findClickableViewInChild(this.childView, (int) motionEvent.getX(), (int) motionEvent.getY());
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (!isEnabled() || !this.childView.isEnabled()) {
            return onTouchEvent;
        }
        boolean contains = this.bounds.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        if (contains) {
            this.previousCoords.set(this.currentCoords.x, this.currentCoords.y);
            this.currentCoords.set((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (this.gestureDetector.onTouchEvent(motionEvent) || this.hasPerformedLongPress) {
            return true;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                setPositionInAdapter();
                this.eventCancelled = false;
                this.pendingPressEvent = new PressedEvent(motionEvent);
                if (!isInScrollingContainer()) {
                    this.pendingPressEvent.run();
                    break;
                } else {
                    cancelPressedEvent();
                    this.prepressed = true;
                    postDelayed(this.pendingPressEvent, (long) ViewConfiguration.getTapTimeout());
                    break;
                }
            case 1:
                this.pendingClickEvent = new PerformClickEvent();
                if (this.prepressed) {
                    this.childView.setPressed(true);
                    postDelayed(new Runnable() {
                        public void run() {
                            MaterialRippleLayout.this.childView.setPressed(false);
                        }
                    }, (long) ViewConfiguration.getPressedStateDuration());
                }
                if (contains) {
                    startRipple(this.pendingClickEvent);
                } else if (!this.rippleHover) {
                    setRadius(0.0f);
                }
                if (!this.rippleDelayClick && contains) {
                    this.pendingClickEvent.run();
                }
                cancelPressedEvent();
                break;
            case 2:
                if (this.rippleHover) {
                    if (contains && !this.eventCancelled) {
                        invalidate();
                    } else if (!contains) {
                        startRipple(null);
                    }
                }
                if (!contains) {
                    cancelPressedEvent();
                    ObjectAnimator objectAnimator = this.hoverAnimator;
                    if (objectAnimator != null) {
                        objectAnimator.cancel();
                    }
                    this.childView.onTouchEvent(motionEvent);
                    this.eventCancelled = true;
                    break;
                }
                break;
            case 3:
                if (this.rippleInAdapter) {
                    this.currentCoords.set(this.previousCoords.x, this.previousCoords.y);
                    this.previousCoords = new Point();
                }
                this.childView.onTouchEvent(motionEvent);
                if (!this.rippleHover) {
                    this.childView.setPressed(false);
                } else if (!this.prepressed) {
                    startRipple(null);
                }
                cancelPressedEvent();
                break;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void cancelPressedEvent() {
        PressedEvent pressedEvent = this.pendingPressEvent;
        if (pressedEvent != null) {
            removeCallbacks(pressedEvent);
            this.prepressed = false;
        }
    }

    /* access modifiers changed from: private */
    public void startHover() {
        if (!this.eventCancelled) {
            ObjectAnimator objectAnimator = this.hoverAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            float sqrt = (float) (Math.sqrt(Math.pow((double) getWidth(), 2.0d) + Math.pow((double) getHeight(), 2.0d)) * 1.2000000476837158d);
            this.hoverAnimator = ObjectAnimator.ofFloat(this, this.radiusProperty, new float[]{(float) this.rippleDiameter, sqrt}).setDuration(HOVER_DURATION);
            this.hoverAnimator.setInterpolator(new LinearInterpolator());
            this.hoverAnimator.start();
        }
    }

    /* access modifiers changed from: private */
    public void startRipple(final Runnable runnable) {
        if (!this.eventCancelled) {
            float endRadius = getEndRadius();
            cancelAnimations();
            this.rippleAnimator = new AnimatorSet();
            this.rippleAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (!MaterialRippleLayout.this.ripplePersistent) {
                        MaterialRippleLayout.this.setRadius(0.0f);
                        MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.this;
                        materialRippleLayout.setRippleAlpha(Integer.valueOf(materialRippleLayout.rippleAlpha));
                    }
                    if (runnable != null && MaterialRippleLayout.this.rippleDelayClick) {
                        runnable.run();
                    }
                    MaterialRippleLayout.this.childView.setPressed(false);
                }
            });
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.radiusProperty, new float[]{this.radius, endRadius});
            ofFloat.setDuration((long) this.rippleDuration);
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ObjectAnimator ofInt = ObjectAnimator.ofInt(this, this.circleAlphaProperty, new int[]{this.rippleAlpha, 0});
            ofInt.setDuration((long) this.rippleFadeDuration);
            ofInt.setInterpolator(new AccelerateInterpolator());
            ofInt.setStartDelay((long) ((this.rippleDuration - this.rippleFadeDuration) - 50));
            if (this.ripplePersistent) {
                this.rippleAnimator.play(ofFloat);
            } else if (getRadius() > endRadius) {
                ofInt.setStartDelay(0);
                this.rippleAnimator.play(ofInt);
            } else {
                this.rippleAnimator.playTogether(new Animator[]{ofFloat, ofInt});
            }
            this.rippleAnimator.start();
        }
    }

    private void cancelAnimations() {
        AnimatorSet animatorSet = this.rippleAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.rippleAnimator.removeAllListeners();
        }
        ObjectAnimator objectAnimator = this.hoverAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private float getEndRadius() {
        int width = getWidth();
        int height = getHeight();
        return ((float) Math.sqrt(Math.pow((double) ((float) (width / 2 > this.currentCoords.x ? width - this.currentCoords.x : this.currentCoords.x)), 2.0d) + Math.pow((double) ((float) (height / 2 > this.currentCoords.y ? height - this.currentCoords.y : this.currentCoords.y)), 2.0d))) * 1.2f;
    }

    private boolean isInScrollingContainer() {
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof ViewGroup)) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public AdapterView findParentAdapterView() {
        AdapterView adapterView = this.parentAdapter;
        if (adapterView != null) {
            return adapterView;
        }
        ViewParent parent = getParent();
        while (!(parent instanceof AdapterView)) {
            try {
                parent = parent.getParent();
            } catch (NullPointerException unused) {
                throw new RuntimeException("Could not find a parent AdapterView");
            }
        }
        this.parentAdapter = (AdapterView) parent;
        return this.parentAdapter;
    }

    private void setPositionInAdapter() {
        if (this.rippleInAdapter) {
            this.positionInAdapter = findParentAdapterView().getPositionForView(this);
        }
    }

    private boolean adapterPositionChanged() {
        if (!this.rippleInAdapter) {
            return false;
        }
        int positionForView = findParentAdapterView().getPositionForView(this);
        boolean z = positionForView != this.positionInAdapter;
        this.positionInAdapter = positionForView;
        if (z) {
            cancelPressedEvent();
            cancelAnimations();
            this.childView.setPressed(false);
            setRadius(0.0f);
        }
        return z;
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [boolean] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [int] */
    /* JADX WARNING: type inference failed for: r1v4, types: [int] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], int, ?[boolean, int, float, short, byte, char]]
      uses: [boolean, ?[int, byte, short, char], int]
      mth insns count: 35
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean findClickableViewInChild(View r6, int r7, int r8) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof android.view.ViewGroup
            r1 = 0
            if (r0 == 0) goto L_0x002e
            r0 = r6
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
        L_0x0008:
            int r2 = r0.getChildCount()
            if (r1 >= r2) goto L_0x004c
            android.view.View r2 = r0.getChildAt(r1)
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>()
            r2.getHitRect(r3)
            boolean r4 = r3.contains(r7, r8)
            if (r4 == 0) goto L_0x002b
            int r6 = r3.left
            int r7 = r7 - r6
            int r6 = r3.top
            int r8 = r8 - r6
            boolean r6 = r5.findClickableViewInChild(r2, r7, r8)
            return r6
        L_0x002b:
            int r1 = r1 + 1
            goto L_0x0008
        L_0x002e:
            android.view.View r7 = r5.childView
            if (r6 == r7) goto L_0x004c
            boolean r7 = r6.isEnabled()
            if (r7 == 0) goto L_0x004b
            boolean r7 = r6.isClickable()
            if (r7 != 0) goto L_0x004a
            boolean r7 = r6.isLongClickable()
            if (r7 != 0) goto L_0x004a
            boolean r6 = r6.isFocusableInTouchMode()
            if (r6 == 0) goto L_0x004b
        L_0x004a:
            r1 = 1
        L_0x004b:
            return r1
        L_0x004c:
            boolean r6 = r6.isFocusableInTouchMode()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.balysv.materialripple.MaterialRippleLayout.findClickableViewInChild(android.view.View, int, int):boolean");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.bounds.set(0, 0, i, i2);
        this.rippleBackground.setBounds(this.bounds);
    }

    public void draw(Canvas canvas) {
        boolean adapterPositionChanged = adapterPositionChanged();
        if (this.rippleOverlay) {
            if (!adapterPositionChanged) {
                this.rippleBackground.draw(canvas);
            }
            super.draw(canvas);
            if (!adapterPositionChanged) {
                if (this.rippleRoundedCorners != 0.0f) {
                    Path path = new Path();
                    RectF rectF = new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
                    float f = this.rippleRoundedCorners;
                    path.addRoundRect(rectF, f, f, Direction.CW);
                    canvas.clipPath(path);
                }
                canvas.drawCircle((float) this.currentCoords.x, (float) this.currentCoords.y, this.radius, this.paint);
                return;
            }
            return;
        }
        if (!adapterPositionChanged) {
            this.rippleBackground.draw(canvas);
            canvas.drawCircle((float) this.currentCoords.x, (float) this.currentCoords.y, this.radius, this.paint);
        }
        super.draw(canvas);
    }

    /* access modifiers changed from: private */
    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float f) {
        this.radius = f;
        invalidate();
    }

    public int getRippleAlpha() {
        return this.paint.getAlpha();
    }

    public void setRippleAlpha(Integer num) {
        this.paint.setAlpha(num.intValue());
        invalidate();
    }

    public void setRippleColor(int i) {
        this.rippleColor = i;
        this.paint.setColor(i);
        this.paint.setAlpha(this.rippleAlpha);
        invalidate();
    }

    public void setRippleOverlay(boolean z) {
        this.rippleOverlay = z;
    }

    public void setRippleDiameter(int i) {
        this.rippleDiameter = i;
    }

    public void setRippleDuration(int i) {
        this.rippleDuration = i;
    }

    public void setRippleBackground(int i) {
        this.rippleBackground = new ColorDrawable(i);
        this.rippleBackground.setBounds(this.bounds);
        invalidate();
    }

    public void setRippleHover(boolean z) {
        this.rippleHover = z;
    }

    public void setRippleDelayClick(boolean z) {
        this.rippleDelayClick = z;
    }

    public void setRippleFadeDuration(int i) {
        this.rippleFadeDuration = i;
    }

    public void setRipplePersistent(boolean z) {
        this.ripplePersistent = z;
    }

    public void setRippleInAdapter(boolean z) {
        this.rippleInAdapter = z;
    }

    public void setRippleRoundedCorners(int i) {
        this.rippleRoundedCorners = (float) i;
        enableClipPathSupportIfNecessary();
    }

    public void setDefaultRippleAlpha(int i) {
        this.rippleAlpha = i;
        this.paint.setAlpha(i);
        invalidate();
    }

    public void performRipple() {
        this.currentCoords = new Point(getWidth() / 2, getHeight() / 2);
        startRipple(null);
    }

    public void performRipple(Point point) {
        this.currentCoords = new Point(point.x, point.y);
        startRipple(null);
    }

    private void enableClipPathSupportIfNecessary() {
        if (VERSION.SDK_INT > 17) {
            return;
        }
        if (this.rippleRoundedCorners != 0.0f) {
            this.layerType = getLayerType();
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            return;
        }
        setLayerType(this.layerType, null);
    }

    static float dpToPx(Resources resources, float f) {
        return TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }
}
