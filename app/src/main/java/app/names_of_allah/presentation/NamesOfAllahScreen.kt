package app.names_of_allah.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.names_of_allah.presentation.viewmodel.NamesViewModel

@Composable
fun NamesOfAllahScreen(viewModel: NamesViewModel = hiltViewModel()) {
    val state = viewModel.uiState

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F2027),
                            Color(0xFF203A43),
                            Color(0xFF2C5364)
                        )
                    )
                )
        ) {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "جاري تحميل أسماء الله الحسنى...",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "حدث خطأ غير متوقع",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp, bottom = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "أسماء الله الحسنى",
                                    fontSize = 28.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(
                                    color = Color.White.copy(alpha = 0.3f),
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                )
                            }
                        }

                        items(state.names) { name ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(20.dp)),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF56CCF2),
                                                    Color(0xFF2F80ED)
                                                )
                                            )
                                        )
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = name.name,
                                            fontSize = 24.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = name.transliteration,
                                            fontSize = 18.sp,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontStyle = FontStyle.Italic
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = translateMeaningWithExplanation(name.en.meaning),
                                            fontSize = 16.sp,
                                            color = Color.White.copy(alpha = 0.95f),
                                            lineHeight = 22.sp
                                        )
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}

val nameMeanings = mapOf(
    "The Beneficent" to "الرحمن — الذي وسعت رحمته جميع الخلق، يغدق النعم بلا تمييز",
    "The Merciful" to "الرحيم — الذي يثيب عباده المؤمنين برحمته الخاصة ومغفرته المتجددة",
    "The King / Eternal Lord" to "الملك — المتصرف في الملكوت، صاحب السيادة المطلقة",
    "The Purest" to "القدوس — المنزه من كل نقص، الكامل في صفاته",
    "The Source of Peace" to "السلام — الذي سلامه يشمل كل شيء، يمنح الأمن والسكينة",
    "The inspirer of faith" to "المؤمن — الذي يؤمن على خلقه بسلامة الإيمان، يبعث الطمأنينة",
    "The Guardian" to "المهيمن — الذي يراقب شأن المخلوقات ويحفظها بعنايته",
    "The Precious / The Most Mighty" to "العزيز — القوي الذي لا يُغلب، العزيز في ملكه",
    "The Compeller" to "الجبار — الذي يجبر كسر عباده، العالي فوق خلقه قهراً وقدراً",
    "The Greatest" to "المتكبر — المتعالي عن صفات النقص، المتفرد بالعظمة",
    "The Creator" to "الخالق — الذي خلق كل شيء بتقدير وإتقان",
    "The Maker of Order" to "البارئ — الذي أوجد المخلوقات من العدم دون مثال سابق",
    "The Shaper of Beauty" to "المصور — الذي صور كل شيء في أحسن تقويم",
    "The Forgiving" to "الغفار — كثير المغفرة لعباده مهما عظمت ذنوبهم",
    "The Subduer" to "القهار — الذي قهر الجبابرة، وخضع له كل شيء",
    "The Giver of All" to "الوهاب — المعطي بلا حدود ولا مقابل",
    "The Provider" to "الرزاق — الذي يرزق خلقه حاجاتهم في كل حين",
    "The Opener" to "الفتاح — الذي يفتح أبواب الخير والنصر لعباده",
    "The Knower of all" to "العليم — الذي لا تخفى عليه خافية، يعلم الغيب والشهادة",
    "The Constrictor" to "القابض — الذي يقبض الأرزاق ويمنع لحكمة",
    "The Reliever" to "الباسط — الذي يوسع في الرزق والرحمة لعباده",
    "The Abaser" to "الخافض — الذي يخفض الجبابرة والظالمين",
    "The Exalter" to "الرافع — الذي يرفع أولياءه وعباده الصالحين",
    "The Bestower of Honour" to "المعز — الذي يمنح العز لمن يشاء",
    "The Humiliator" to "المذل — الذي يذل من يستحق الذل بعلمه وعدله",
    "The Hearer of all" to "السميع — الذي يسمع كل شيء، لا تخفى عليه الأصوات",
    "The Seer of all" to "البصير — الذي يرى كل شيء ظاهر وخفي",
    "The Judge" to "الحكم — الذي يفصل بين الخلق بالحق والعدل",
    "The Just" to "العدل — الذي لا يظلم أحداً ويضع كل شيء موضعه",
    "The Subtle One" to "اللطيف — الذي يدبر الأمور بلطف وخفاء",
    "The All Aware" to "الخبير — الذي يعلم بواطن الأمور ودقائقها",
    "The Forebearing" to "الحليم — الذي لا يعجل بعقوبة المسيء",
    "The Maginificent" to "العظيم — الذي له العظمة المطلقة في ذاته وصفاته",
    "The Great Forgiver" to "الغفور — الذي يغفر الذنوب جميعاً لمن تاب",
    "The Rewarder of Thankfulness" to "الشكور — الذي يثيب على القليل من العمل الكثير من الأجر",
    "The Highest" to "العلي — المتعالي في ذاته وصفاته، فوق كل شيء",
    "The Most Great" to "الكبير — الذي كل شيء دونه صغير، المتعالي عن النقائص",
    "The Preserver" to "الحفيظ — الحافظ لكل شيء من الزوال والضياع",
    "The Nourisher" to "المقيت — الذي يوصل الأرزاق والأسباب إلى خلقه",
    "The Reckoner" to "الحسيب — الذي يكفي عباده ويحاسبهم بعدله",
    "The Majestic" to "الجليل — العظيم في ذاته وصفاته وأفعاله",
    "The Generous" to "الكريم — كثير الخير، عظيم العطاء",
    "The Watchful One" to "الرقيب — الذي لا يغيب عنه شيء، يراقب عباده",
    "The Responder to Prayer" to "المجيب — الذي يجيب دعاء من دعاه وسؤاله",
    "The All Comprehending" to "الواسع — الذي وسع كل شيء رحمة وعلماً",
    "The Perfectly Wise" to "الحكيم — الذي يضع الأمور في مواضعها بعلم وحكمة",
    "The Loving One" to "الودود — الذي يحب عباده المؤمنين ويحبونه",
    "The Most Glorious One" to "المجيد — الكامل في الشرف والمجد والكرم",
    "The Resurrector" to "الباعث — الذي يبعث الخلق للحساب والجزاء",
    "The Witness" to "الشهيد — الذي لا يغيب عنه شيء، الحاضر لكل الأحداث",
    "The Truth" to "الحق — الموجود بحق، الذي لا يتغير ولا يزول",
    "The Trustee" to "الوكيل — الذي يتولى أمر عباده ويدبر شؤونهم",
    "The Possessor of all strength" to "القوي — كامل القوة والقدرة، لا يعجزه شيء",
    "The Forceful" to "المتين — الثابت الذي لا تهزه قوة ولا يؤثر فيه شيء",
    "The Protector" to "الولي — الذي يتولى الصالحين بنصره ورعايته",
    "The Praised" to "الحميد — المستحق للحمد والثناء في كل حال",
    "The Appraiser" to "المحصي — الذي أحصى كل شيء عدداً وعلمه",
    "The Originator" to "المبدئ — الذي بدأ الخلق من العدم",
    "The Restorer" to "المعيد — الذي يعيد الخلق بعد موتهم للحساب",
    "The Giver of life" to "المحيي — الذي يحيي الأجساد والنفوس بأمره",
    "The Taker of life" to "المميت — الذي يميت الخلق بقدرته",
    "The Ever Living" to "الحي — الذي لا يموت، قائم بذاته وصفاته",
    "The Self Existing" to "القيوم — القائم على كل نفس بما كسبت، المدبر لشؤون الخلق",
    "The Finder" to "الواجد — الغني الذي لا يحتاج إلى شيء",
    "The Glorious" to "الماجد — المتصف بالمجد والكرم والجلال",
    "The Only One" to "الواحد — المتفرد بالألوهية دون شريك",
    "The Indivisible" to "الأحد — الذي لا يتجزأ ولا شبيه له",
    "The Supreme Provider" to "الصمد — الذي يُقصد في الحوائج، الكامل في صفاته",
    "The Powerful" to "القادر — الذي يقدر على كل شيء بإرادته",
    "The Creator of all power" to "المقتدر — الذي يبلغ أقصى درجات القدرة والتمكن",
    "The Expediter" to "المقدم — الذي يقدم من يشاء بما يشاء",
    "The Delayer" to "المؤخر — الذي يؤخر من يشاء لحكمة يعلمها",
    "The First" to "الأول — الذي ليس قبله شيء",
    "The Last" to "الآخر — الذي ليس بعده شيء",
    "The Manifest" to "الظاهر — الذي ظهر في دلائل قدرته وآثاره",
    "The Hidden" to "الباطن — الذي لا تدركه الأبصار، ولا يعلم كنهه أحد",
    "The Governor" to "الوالي — المتصرف في خلقه كيف يشاء",
    "The Supreme One" to "المتعالي — المتعالي عن كل نقص وعيب",
    "The Doer of Good" to "البر — الذي يتفضل على عباده بجزيل النعم",
    "The Guide to Repentence" to "التواب — الذي يفتح باب التوبة لعباده ويقبلها",
    "The Avenger" to "المنتقم — الذي ينتقم من أعدائه بعدل وحكمة",
    "The Forgiver" to "العفو — الذي يمحو الذنوب ويتجاوز عن السيئات",
    "The Clement" to "الرؤوف — شديد الرحمة والرأفة بعباده",
    "The Owner / Soverign of All" to "مالك الملك — الذي يملك كل شيء ويصرفه كيف يشاء",
    "Possessor of Majesty and Bounty" to "ذو الجلال والإكرام — صاحب العظمة والكمال والإكرام",
    "The Equitable One" to "المقسط — الذي يعدل بين خلقه ولا يظلم أحداً",
    "The Gatherer" to "الجامع — الذي يجمع الخلق ليوم لا ريب فيه",
    "The Rich One" to "الغني — الغني عن كل شيء، وكل الخلق بحاجة إليه",
    "The Enricher" to "المغني — الذي يرزق من يشاء ويغنيه من فضله",
    "The Preventer of harm" to "المانع — الذي يمنع لحكمة ويعطي لحكمة",
    "The Creator of the harmful" to "الضار — الذي يقدر الضر لأسباب عظيمة",
    "The Bestower of Benefits" to "النافع — الذي ينفع عباده بما يشاء",
    "The Light" to "النور — الذي ينير القلوب بنوره، وبه يهتدون",
    "The Guider" to "الهادي — الذي يهدي من يشاء إلى طريق الحق",
    "The Incomparable Creator" to "البديع — الذي خلق الخلق بإبداع دون مثال سابق",
    "The Everlasting One" to "الباقي — الذي لا يفنى ولا يزول",
    "The Inhertior" to "الوارث — الذي يبقى بعد فناء الخلق، يرث كل شيء",
    "The Most Righteous Guide" to "الرشيد — الذي يرشد خلقه لما فيه صلاحهم",
    "The Patient One" to "الصبور — الذي لا يعجل على عباده رغم معاصيهم"
)


fun translateMeaningWithExplanation(english: String): String {
    val key = english.trim()
    return nameMeanings[key] ?: "المعنى: $english"
}